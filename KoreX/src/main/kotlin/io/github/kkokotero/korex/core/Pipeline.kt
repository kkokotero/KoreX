package io.github.kkokotero.korex.core

class Pipeline<I, O> private constructor(
    private val steps: List<Step>,
    private val errorHandler: ((Throwable, String) -> Unit)?,
) {
    private data class Step(
        val name: String,
        val transform: (Any?) -> Any?,
    )

    fun <N> then(name: String, transform: (O) -> N): Pipeline<I, N> = Pipeline(
        steps = steps + Step(name) { input -> transform(input as O) },
        errorHandler = errorHandler,
    )

    fun tap(name: String, action: (O) -> Unit): Pipeline<I, O> = Pipeline(
        steps = steps + Step(name) { input ->
            action(input as O)
            input
        },
        errorHandler = errorHandler,
    )

    fun fail(handler: (Throwable, String) -> Unit): Pipeline<I, O> = Pipeline(
        steps = steps,
        errorHandler = handler,
    )

    @Suppress("UNCHECKED_CAST")
    fun run(input: I): Result<O> {
        var current: Any? = input
        for (step in steps) {
            current = try {
                step.transform(current)
            } catch (error: Throwable) {
                errorHandler?.invoke(error, step.name)
                return failure(error)
            }
        }
        return success(current as O)
    }

    companion object {
        fun <T> create(): Pipeline<T, T> = Pipeline(emptyList(), null)
    }
}

fun <T> pipeline(): Pipeline<T, T> = Pipeline.create()
