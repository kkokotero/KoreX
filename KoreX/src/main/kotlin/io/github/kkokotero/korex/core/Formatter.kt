package io.github.kkokotero.korex.core

fun interface Formatter<T> {
    fun format(value: T): String
}

fun <T> formatter(block: (T) -> String): Formatter<T> = Formatter(block)
