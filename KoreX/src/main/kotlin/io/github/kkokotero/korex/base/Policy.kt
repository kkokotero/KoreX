package io.github.kkokotero.korex.base

abstract class Policy<T> {
    abstract fun allows(value: T): Boolean
}
