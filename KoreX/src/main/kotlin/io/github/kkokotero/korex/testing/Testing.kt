package io.github.kkokotero.korex.testing

import io.github.kkokotero.korex.core.Result
import io.github.kkokotero.korex.network.ApiResult
import io.github.kkokotero.korex.core.Logger
import io.github.kkokotero.korex.core.LogLevel
import io.github.kkokotero.korex.data.InMemoryLocalDataSource

class FakeLogger : Logger {
    data class Entry(val level: LogLevel, val tag: String, val message: String, val throwable: Throwable?)
    val entries = mutableListOf<Entry>()

    override fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        entries += Entry(level, tag, message, throwable)
    }
}

fun <T> assertSuccess(result: Result<T>): T = when (result) {
    is Result.Success -> result.value
    is Result.Failure -> error("Expected success, got failure: ${result.error.message}")
}

fun <T> assertFailure(result: Result<T>): Throwable = when (result) {
    is Result.Success -> error("Expected failure, got success")
    is Result.Failure -> result.error
}

fun <T> assertApiSuccess(result: ApiResult<T>): T = when (result) {
    is ApiResult.Success -> result.value
    is ApiResult.Failure -> error("Expected success, got ${result.error.message}")
    ApiResult.Offline -> error("Expected success, got offline")
}

fun <K, V> inMemoryDataSource(): InMemoryLocalDataSource<K, V> = InMemoryLocalDataSource()
