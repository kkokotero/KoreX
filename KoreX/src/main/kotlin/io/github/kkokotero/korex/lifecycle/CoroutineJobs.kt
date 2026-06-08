package io.github.kkokotero.korex.lifecycle

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ManagedJobScope(
    parent: Job = SupervisorJob(),
    private val onError: (Throwable) -> Unit = {},
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> onError(throwable) }
    val scope: CoroutineScope = CoroutineScope(parent + exceptionHandler)

    fun launch(block: suspend CoroutineScope.() -> Unit): Job = scope.launch(block = block)

    fun cancelChildren() {
        scope.coroutineContext.cancelChildren()
    }
}

class ResilientTaskRunner(
    private val autoCancelJob: AutoCancelJob = AutoCancelJob(),
    private val retry: suspend (Throwable, Int) -> Boolean = { _, _ -> false },
) {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job = autoCancelJob.scope.launch {
        var attempt = 1
        while (true) {
            try {
                block()
                return@launch
            } catch (throwable: Throwable) {
                if (!retry(throwable, attempt)) throw throwable
                attempt++
            }
        }
    }
}
