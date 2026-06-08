package io.github.kkokotero.korex.base

abstract class Resolver<I, O> {
    abstract suspend fun resolve(input: I): O
}
