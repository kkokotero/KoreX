package io.github.kkokotero.korex.base

abstract class UseCase<I, O> {
    abstract suspend fun execute(input: I): O
}
