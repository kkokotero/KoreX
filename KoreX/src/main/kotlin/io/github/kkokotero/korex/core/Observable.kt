package io.github.kkokotero.korex.core

import java.util.concurrent.CopyOnWriteArrayList

class ObservableValue<T>(initial: T) {
    private val listeners = CopyOnWriteArrayList<(T) -> Unit>()

    @Volatile
    var value: T = initial
        set(newValue) {
            field = newValue
            listeners.forEach { it(newValue) }
        }

    fun observe(listener: (T) -> Unit): () -> Unit {
        listeners += listener
        listener(value)
        return { listeners -= listener }
    }
}

fun <T> observable(initial: T): ObservableValue<T> = ObservableValue(initial)
