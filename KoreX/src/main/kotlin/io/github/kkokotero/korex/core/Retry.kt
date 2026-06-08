package io.github.kkokotero.korex.core

data class RetryPolicy(
    val maxAttempts: Int = 3,
    val shouldRetry: (Throwable, Int) -> Boolean = { _, _ -> true },
)

class Retry(private val policy: RetryPolicy = RetryPolicy()) {
    fun <T> run(block: () -> T): Result<T> {
        var lastError: Throwable? = null
        repeat(policy.maxAttempts) { index ->
            val attempt = index + 1
            try {
                return success(block())
            } catch (error: Throwable) {
                lastError = error
                if (!policy.shouldRetry(error, attempt)) {
                    return failure(error)
                }
            }
        }
        return failure(lastError ?: IllegalStateException("Retry failed without an exception"))
    }
}

fun retry(policy: RetryPolicy = RetryPolicy()): Retry = Retry(policy)
