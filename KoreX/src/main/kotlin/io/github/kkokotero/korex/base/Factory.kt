package io.github.kkokotero.korex.base

abstract class Factory<T> {
    abstract fun create(): T
}
