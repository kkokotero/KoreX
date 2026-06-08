package io.github.kkokotero.korex.base

data class Snapshot<T>(
    val current: T,
    val previous: T? = null,
    val version: Long = System.currentTimeMillis(),
)
