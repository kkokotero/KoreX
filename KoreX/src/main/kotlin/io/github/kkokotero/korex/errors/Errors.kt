package io.github.kkokotero.korex.errors

data class UserFacingError(
    val title: String,
    val description: String,
    val cause: Throwable? = null,
)

fun interface ErrorMapper {
    fun map(error: Throwable): UserFacingError
}

fun interface GlobalErrorHandler {
    fun handle(error: Throwable)
}

fun userFacingError(message: String, cause: Throwable? = null): UserFacingError =
    UserFacingError(title = "Something went wrong", description = message, cause = cause)
