package io.github.kkokotero.korex.base

abstract class Transformer<I, O> {
    abstract fun transform(input: I): O
}
