package io.github.kkokotero.korex.sample.domain

data class HomeUiState(
    val title: String = "KoreX Sample",
    val subtitle: String = "Running against the KoreX source module",
    val attempts: Int = 0,
    val pipelineResult: String = "Idle",
    val validationResult: String = "Not validated",
    val networkResult: String = "No network call yet",
    val cachedValue: String = "No cache yet",
    val notificationPermissionState: String = "Not requested",
    val notificationResult: String = "No notification yet",
    val snapshotVersion: Long = 0L,
    val lastSignal: String = "No signal",
    val errorMessage: String? = null,
)
