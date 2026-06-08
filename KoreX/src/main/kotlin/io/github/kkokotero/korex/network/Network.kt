package io.github.kkokotero.korex.network

import io.github.kkokotero.korex.core.Result
import io.github.kkokotero.korex.core.resultOf
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.Serializable
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Request

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Response

interface SerializablePayload : Serializable

enum class HttpMethod { GET, POST, PUT, PATCH, DELETE }

data class NetworkRequest<Body : Any, Response : Any>(
    val path: String,
    val method: HttpMethod = HttpMethod.GET,
    val body: Body? = null,
    val headers: Map<String, String> = emptyMap(),
    val responseType: Class<Response>,
)

inline fun <reified Body : Any, reified Response : Any> networkRequest(
    path: String,
    method: HttpMethod = HttpMethod.GET,
    body: Body? = null,
    headers: Map<String, String> = emptyMap(),
): NetworkRequest<Body, Response> = NetworkRequest(
    path = path,
    method = method,
    body = body,
    headers = headers,
    responseType = Response::class.java,
)

data class ApiError(
    val message: String,
    val code: Int? = null,
    val cause: Throwable? = null,
)

sealed interface ApiResult<out T> {
    data class Success<T>(val value: T) : ApiResult<T>
    data class Failure(val error: ApiError) : ApiResult<Nothing>
    data object Offline : ApiResult<Nothing>
}

fun <T> apiSuccess(value: T): ApiResult<T> = ApiResult.Success(value)
fun <T> apiFailure(error: ApiError): ApiResult<T> = ApiResult.Failure(error)

fun <T> safeRequest(
    mapper: (Throwable) -> ApiError = { ApiError(it.message ?: "Unknown network error", cause = it) },
    block: () -> T,
): ApiResult<T> = try {
    apiSuccess(block())
} catch (error: Throwable) {
    apiFailure(mapper(error))
}

fun interface ErrorMapper {
    fun map(throwable: Throwable): ApiError
}

interface JsonCodec {
    fun encode(value: Any): String

    fun <T : Any> decode(json: String, type: Class<T>): T
}

class MoshiJsonCodec(
    moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build(),
) : JsonCodec {
    private val moshi = moshi

    override fun encode(value: Any): String {
        @Suppress("UNCHECKED_CAST")
        val adapter = moshi.adapter(value::class.java as Class<Any>)
        return adapter.toJson(value)
    }

    override fun <T : Any> decode(json: String, type: Class<T>): T {
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter.fromJson(json) ?: error("Unable to decode ${type.simpleName}")
    }
}

interface Interceptor {
    fun <Body : Any, Response : Any> intercept(
        request: NetworkRequest<Body, Response>,
        proceed: () -> ApiResult<Response>,
    ): ApiResult<Response>
}

data class RetryPolicy(
    val maxAttempts: Int = 3,
    val shouldRetry: (Throwable, Int) -> Boolean = { _, _ -> true },
)

interface OfflineStrategy {
    fun isOffline(): Boolean
}

interface HttpClient {
    fun <Body : Any, Response : Any> execute(request: NetworkRequest<Body, Response>): ApiResult<Response>
}

class HttpCallBuilder<Body : Any, Response : Any>(
    private val path: String,
    private val method: HttpMethod,
    private val responseType: Class<Response>,
) {
    private var body: Body? = null
    private val headers = linkedMapOf<String, String>()

    fun body(value: Body) {
        body = value
    }

    fun header(name: String, value: String) {
        headers[name] = value
    }

    fun build(): NetworkRequest<Body, Response> = NetworkRequest(
        path = path,
        method = method,
        body = body,
        headers = headers.toMap(),
        responseType = responseType,
    )
}

inline fun <reified Response : Any, reified Body : Any> HttpClient.get(
    path: String,
    block: HttpCallBuilder<Body, Response>.() -> Unit = {},
): ApiResult<Response> = execute(
    HttpCallBuilder<Body, Response>(path, HttpMethod.GET, Response::class.java).apply(block).build(),
)

inline fun <reified Response : Any, reified Body : Any> HttpClient.post(
    path: String,
    block: HttpCallBuilder<Body, Response>.() -> Unit = {},
): ApiResult<Response> = execute(
    HttpCallBuilder<Body, Response>(path, HttpMethod.POST, Response::class.java).apply(block).build(),
)

inline fun <reified Response : Any, reified Body : Any> HttpClient.put(
    path: String,
    block: HttpCallBuilder<Body, Response>.() -> Unit = {},
): ApiResult<Response> = execute(
    HttpCallBuilder<Body, Response>(path, HttpMethod.PUT, Response::class.java).apply(block).build(),
)

inline fun <reified Response : Any, reified Body : Any> HttpClient.patch(
    path: String,
    block: HttpCallBuilder<Body, Response>.() -> Unit = {},
): ApiResult<Response> = execute(
    HttpCallBuilder<Body, Response>(path, HttpMethod.PATCH, Response::class.java).apply(block).build(),
)

inline fun <reified Response : Any, reified Body : Any> HttpClient.delete(
    path: String,
    block: HttpCallBuilder<Body, Response>.() -> Unit = {},
): ApiResult<Response> = execute(
    HttpCallBuilder<Body, Response>(path, HttpMethod.DELETE, Response::class.java).apply(block).build(),
)

interface WebSocketClient {
    fun connect(url: String): Result<Unit>
    fun send(message: String): Result<Unit>
    fun close(): Result<Unit>
}

class OkHttpWebSocketClient(
    private val client: OkHttpClient,
) : WebSocketClient {
    private var socket: okhttp3.WebSocket? = null

    override fun connect(url: String): Result<Unit> = resultOf {
        val request = okhttp3.Request.Builder().url(url).build()
        socket = client.newWebSocket(request, object : okhttp3.WebSocketListener() {})
        Unit
    }

    override fun send(message: String): Result<Unit> = resultOf {
        checkNotNull(socket) { "WebSocket not connected" }
            .send(message)
        Unit
    }

    override fun close(): Result<Unit> = resultOf {
        socket?.close(1000, "Closed by client")
        Unit
    }
}

class OkHttpHttpClient(
    private val baseUrl: String,
    private val jsonCodec: JsonCodec = MoshiJsonCodec(),
    private val errorMapper: ErrorMapper = ErrorMapper { ApiError(it.message ?: "Unknown network error", cause = it) },
    private val interceptors: List<Interceptor> = emptyList(),
    private val offlineStrategy: OfflineStrategy? = null,
    private val retryPolicy: RetryPolicy = RetryPolicy(),
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build(),
) : HttpClient {
    override fun <Body : Any, Response : Any> execute(request: NetworkRequest<Body, Response>): ApiResult<Response> {
        if (offlineStrategy?.isOffline() == true) return ApiResult.Offline
        var lastError: Throwable? = null
        repeat(retryPolicy.maxAttempts) { index ->
            val attempt = index + 1
            val result = intercept(request) {
                safeRequest(errorMapper::map) { executeOnce(request) }
            }
            when (result) {
                is ApiResult.Success -> return result
                is ApiResult.Offline -> return result
                is ApiResult.Failure -> {
                    lastError = result.error.cause ?: IllegalStateException(result.error.message)
                    if (!retryPolicy.shouldRetry(lastError!!, attempt)) return result
                }
            }
        }
        return apiFailure(errorMapper.map(lastError ?: IllegalStateException("Retry failed")))
    }

    private fun <Body : Any, Response : Any> intercept(
        request: NetworkRequest<Body, Response>,
        proceed: () -> ApiResult<Response>,
    ): ApiResult<Response> {
        var chain = proceed
        interceptors.asReversed().forEach { interceptor ->
            val next = chain
            chain = { interceptor.intercept(request, next) }
        }
        return chain()
    }

    private fun <Body : Any, Response : Any> executeOnce(request: NetworkRequest<Body, Response>): Response {
        val body = request.body?.let { encodeBody(it) }
        val httpRequest = okhttp3.Request.Builder()
            .url(baseUrl.trimEnd('/') + "/" + request.path.trimStart('/'))
            .method(request.method.name, body)
            .apply {
                request.headers.forEach { (key, value) -> header(key, value) }
            }
            .build()

        val response = client.newCall(httpRequest).execute()
        response.use {
            if (!it.isSuccessful) {
                throw IllegalStateException("HTTP ${it.code}: ${it.message}")
            }
            val responseBody = it.body?.string().orEmpty()
            if (request.responseType == String::class.java) {
                @Suppress("UNCHECKED_CAST")
                return responseBody as Response
            }
            if (request.responseType == Unit::class.java || request.responseType == Void::class.java) {
                @Suppress("UNCHECKED_CAST")
                return Unit as Response
            }
            return jsonCodec.decode(responseBody, request.responseType)
        }
    }

    private fun encodeBody(value: Any): okhttp3.RequestBody = when (value) {
        is String -> value.toRequestBody("text/plain; charset=utf-8".toMediaType())
        is ByteArray -> value.toRequestBody("application/octet-stream".toMediaType())
        else -> jsonCodec.encode(value).toRequestBody("application/json; charset=utf-8".toMediaType())
    }
}

fun httpClient(
    baseUrl: String,
    jsonCodec: JsonCodec = MoshiJsonCodec(),
    errorMapper: ErrorMapper = ErrorMapper { ApiError(it.message ?: "Unknown network error", cause = it) },
    interceptors: List<Interceptor> = emptyList(),
    offlineStrategy: OfflineStrategy? = null,
    retryPolicy: RetryPolicy = RetryPolicy(),
    client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build(),
): HttpClient = OkHttpHttpClient(
    baseUrl = baseUrl,
    jsonCodec = jsonCodec,
    errorMapper = errorMapper,
    interceptors = interceptors,
    offlineStrategy = offlineStrategy,
    retryPolicy = retryPolicy,
    client = client,
)

fun <T> Result<T>.toApiResult(mapper: (Throwable) -> ApiError): ApiResult<T> = when (this) {
    is Result.Success -> ApiResult.Success(value)
    is Result.Failure -> apiFailure(mapper(error))
}
