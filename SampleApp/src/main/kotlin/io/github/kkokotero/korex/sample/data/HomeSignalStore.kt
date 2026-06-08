package io.github.kkokotero.korex.sample.data

import io.github.kkokotero.korex.base.Signal

class HomeSignalStore {
    private val signals = mutableListOf<Signal<String>>()

    fun push(value: String): Signal<String> {
        val signal = Signal(value)
        signals += signal
        return signal
    }

    fun latest(): Signal<String>? = signals.lastOrNull()
}
