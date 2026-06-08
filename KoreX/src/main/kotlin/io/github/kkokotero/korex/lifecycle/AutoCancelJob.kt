package io.github.kkokotero.korex.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class AutoCancelJob(
    private val parent: Job = SupervisorJob(),
) {
    val scope: CoroutineScope = CoroutineScope(parent + Dispatchers.Main.immediate)

    fun cancel() {
        parent.cancel()
    }
}

class AutoCancelJobObserver(
    private val autoCancelJob: AutoCancelJob,
) : DefaultLifecycleObserver {
    override fun onDestroy(owner: LifecycleOwner) {
        autoCancelJob.cancel()
    }
}
