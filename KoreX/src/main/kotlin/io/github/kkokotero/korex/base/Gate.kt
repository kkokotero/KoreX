package io.github.kkokotero.korex.base

abstract class Gate<T> {
    abstract suspend fun open(context: T): Boolean
}
