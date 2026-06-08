package io.github.kkokotero.korex.base

abstract class Reducer<S, I> {
    abstract fun reduce(state: S, input: I): S
}
