package io.github.kkokotero.korex.base

abstract class Operation<I, O> {
    abstract suspend fun execute(input: I): O
}
