package io.github.kkokotero.korex.core

sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Failure(val error: Throwable) : Result<Nothing>
}

typealias KorexResult<T> = Result<T>

fun <T> success(value: T): Result<T> = Result.Success(value)
fun <T> failure(error: Throwable): Result<T> = Result.Failure(error)

fun <T> resultOf(block: () -> T): Result<T> = try {
    success(block())
} catch (error: Throwable) {
    failure(error)
}

fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> = apply {
    if (this is Result.Success) block(value)
}

fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> = apply {
    if (this is Result.Failure) block(error)
}

fun <T> Result<T>.success(block: (T) -> Unit): Result<T> = onSuccess(block)
fun <T> Result<T>.failure(block: (Throwable) -> Unit): Result<T> = onFailure(block)
fun <T> Result<T>.fail(block: (Throwable) -> Unit): Result<T> = onFailure(block)

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> success(transform(value))
    is Result.Failure -> failure(error)
}

inline fun <T> Result<T>.recover(transform: (Throwable) -> T): Result<T> = when (this) {
    is Result.Success -> this
    is Result.Failure -> success(transform(error))
}

inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
    is Result.Success -> transform(value)
    is Result.Failure -> failure(error)
}

inline fun <T, R> Result<T>.fold(onSuccess: (T) -> R, onFailure: (Throwable) -> R): R = when (this) {
    is Result.Success -> onSuccess(value)
    is Result.Failure -> onFailure(error)
}

fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> value
    is Result.Failure -> null
}

fun <T> Result<T>.exceptionOrNull(): Throwable? = when (this) {
    is Result.Success -> null
    is Result.Failure -> error
}
