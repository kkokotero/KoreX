package io.github.kkokotero.korex.sample.domain

sealed interface HomeEvent {
    data object LoadGreeting : HomeEvent
    data object RunPipeline : HomeEvent
    data object RetryOperation : HomeEvent
    data object RunValidation : HomeEvent
    data object RunNetwork : HomeEvent
    data object ResetError : HomeEvent
}
