package io.github.kkokotero.korex.base

abstract class Provider<T> {
    abstract fun get(): T
}
