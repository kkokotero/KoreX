package io.github.kkokotero.korex.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun interface LifecycleTask {
    fun start(scope: CoroutineScope): Job
}

class LifecycleTaskGroup {
    private val tasks = linkedSetOf<LifecycleTask>()
    private val jobs = linkedMapOf<LifecycleTask, Job>()

    fun add(task: LifecycleTask) {
        tasks += task
    }

    fun remove(task: LifecycleTask) {
        jobs.remove(task)?.cancel()
        tasks -= task
    }

    fun start(scope: CoroutineScope) {
        tasks.forEach { task ->
            jobs[task] = task.start(scope)
        }
    }

    fun cancel() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
    }
}
