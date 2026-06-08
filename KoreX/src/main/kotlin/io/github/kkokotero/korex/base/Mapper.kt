package io.github.kkokotero.korex.base

abstract class Mapper<I, O> {
    abstract fun map(input: I): O
}
