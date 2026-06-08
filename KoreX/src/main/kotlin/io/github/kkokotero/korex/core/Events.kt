package io.github.kkokotero.korex.core

import java.util.concurrent.CopyOnWriteArrayList

class EventBus<E> {
    private val listeners = CopyOnWriteArrayList<(E) -> Unit>()

    fun publish(event: E) {
        listeners.forEach { it(event) }
    }

    fun subscribe(listener: (E) -> Unit): () -> Unit {
        listeners += listener
        return { listeners -= listener }
    }
}

fun <E> eventBus(): EventBus<E> = EventBus()
