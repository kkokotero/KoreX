package io.github.kkokotero.korex.errors

data class KorexError(
    val code: String,
    val message: String? = null,
    val cause: Throwable? = null,
)

open class KorexException(
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
