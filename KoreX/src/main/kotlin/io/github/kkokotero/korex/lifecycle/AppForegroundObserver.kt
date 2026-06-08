package io.github.kkokotero.korex.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class AppForegroundObserver(
    private val onForeground: () -> Unit,
    private val onBackground: () -> Unit,
) : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        onForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        onBackground()
    }
}

fun observeAppForeground(
    onForeground: () -> Unit,
    onBackground: () -> Unit,
): AppForegroundObserver {
    val observer = AppForegroundObserver(onForeground, onBackground)
    ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    return observer
}
