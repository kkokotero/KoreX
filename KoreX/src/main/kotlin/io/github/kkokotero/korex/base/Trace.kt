package io.github.kkokotero.korex.base

abstract class Trace<T> {
    abstract fun record(value: T)
}
