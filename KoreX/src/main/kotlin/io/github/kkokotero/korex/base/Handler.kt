package io.github.kkokotero.korex.base

abstract class Handler<I> {
    abstract suspend fun handle(input: I)
}
