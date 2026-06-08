package io.github.kkokotero.korex.sample.network

import io.github.kkokotero.korex.network.ApiError
import io.github.kkokotero.korex.network.ApiResult
import io.github.kkokotero.korex.network.HttpClient
import io.github.kkokotero.korex.network.NetworkRequest
import io.github.kkokotero.korex.network.apiFailure
import io.github.kkokotero.korex.network.apiSuccess

class FakeHttpClient : HttpClient {
    override fun <Body : Any, Response : Any> execute(request: NetworkRequest<Body, Response>): ApiResult<Response> {
        @Suppress("UNCHECKED_CAST")
        return if (request.path.contains("fail")) {
            apiFailure(ApiError("Simulated network failure", 500, null))
        } else {
            val payload = buildString {
                append("fake-response{")
                append("path=").append(request.path)
                request.body?.let { append(", body=").append(it) }
                if (request.headers.isNotEmpty()) append(", headers=").append(request.headers)
                append('}')
            }
            apiSuccess(payload as Response)
        }
    }
}
