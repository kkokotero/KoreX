package io.github.kkokotero.korex.base

abstract class Contract<I, O> {
    abstract fun validate(input: I): Boolean
    abstract suspend fun resolve(input: I): O
}
