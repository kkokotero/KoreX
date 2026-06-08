package io.github.kkokotero.korex.base

abstract class Mutation<T> {
    abstract suspend fun apply(current: T): T
}
