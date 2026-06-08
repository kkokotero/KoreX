package io.github.kkokotero.korex

import io.github.kkokotero.korex.core.EventBus
import io.github.kkokotero.korex.core.ObservableValue
import io.github.kkokotero.korex.core.Pipeline
import io.github.kkokotero.korex.core.Retry
import io.github.kkokotero.korex.core.Result
import io.github.kkokotero.korex.core.consoleLogger as coreConsoleLogger
import io.github.kkokotero.korex.core.eventBus as coreEventBus
import io.github.kkokotero.korex.core.observable as coreObservable
import io.github.kkokotero.korex.core.pipeline as corePipeline
import io.github.kkokotero.korex.core.resultOf as coreResultOf
import io.github.kkokotero.korex.core.retry as coreRetry
import io.github.kkokotero.korex.network.ApiError
import io.github.kkokotero.korex.network.ErrorMapper
import io.github.kkokotero.korex.network.HttpClient
import io.github.kkokotero.korex.network.Interceptor
import io.github.kkokotero.korex.network.JsonCodec
import io.github.kkokotero.korex.network.MoshiJsonCodec
import io.github.kkokotero.korex.network.OfflineStrategy
import io.github.kkokotero.korex.network.OkHttpHttpClient
import io.github.kkokotero.korex.network.RetryPolicy
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

fun <T> pipeline(): Pipeline<T, T> = corePipeline()

fun <T> observable(initial: T): ObservableValue<T> = coreObservable(initial)

fun <E> eventBus(): EventBus<E> = coreEventBus()

fun retry(): Retry = coreRetry()

fun <T> resultOf(block: () -> T): Result<T> = coreResultOf(block)

fun consoleLogger() = coreConsoleLogger()

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
