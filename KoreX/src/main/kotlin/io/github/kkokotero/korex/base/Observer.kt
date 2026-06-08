package io.github.kkokotero.korex.base

abstract class Observer<T> {
    abstract fun observe(value: T)
}
