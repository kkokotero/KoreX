package io.github.kkokotero.korex.core

class Session<T>(initial: T) {
    private val observable = ObservableValue(initial)

    var value: T
        get() = observable.value
        set(newValue) {
            observable.value = newValue
        }

    fun observe(listener: (T) -> Unit): () -> Unit = observable.observe(listener)
}

fun <T> session(initial: T): Session<T> = Session(initial)
