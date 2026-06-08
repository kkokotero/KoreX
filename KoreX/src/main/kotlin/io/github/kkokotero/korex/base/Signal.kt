package io.github.kkokotero.korex.base

open class Signal<T>(
    val value: T,
    val emittedAt: Long = System.currentTimeMillis(),
)
