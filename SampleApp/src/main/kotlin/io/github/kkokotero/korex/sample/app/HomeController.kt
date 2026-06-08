package io.github.kkokotero.korex.sample.app

import android.content.Context
import io.github.kkokotero.korex.base.Gate
import io.github.kkokotero.korex.android.PermissionManager
import io.github.kkokotero.korex.android.Permissions
import io.github.kkokotero.korex.sample.base.GreetingContract
import io.github.kkokotero.korex.sample.base.GreetingProvider
import io.github.kkokotero.korex.sample.base.GreetingResolver
import io.github.kkokotero.korex.sample.base.HomeOperation
import io.github.kkokotero.korex.sample.base.HomeReducer
import io.github.kkokotero.korex.sample.base.HomeTrace
import io.github.kkokotero.korex.sample.base.MessageMapper
import io.github.kkokotero.korex.sample.base.NetworkAwareHandler
import io.github.kkokotero.korex.sample.base.RetryableUseCase
import io.github.kkokotero.korex.sample.base.SampleCapability
import io.github.kkokotero.korex.sample.base.StateObserver
import io.github.kkokotero.korex.sample.base.TitleTransformer
import io.github.kkokotero.korex.core.EventBus
import io.github.kkokotero.korex.core.Logger
import io.github.kkokotero.korex.core.ObservableValue
import io.github.kkokotero.korex.core.Retry
import io.github.kkokotero.korex.core.Validator
import io.github.kkokotero.korex.core.fold
import io.github.kkokotero.korex.core.onFailure
import io.github.kkokotero.korex.core.onSuccess
import io.github.kkokotero.korex.core.pipeline
import io.github.kkokotero.korex.data.InMemoryLocalDataSource
import io.github.kkokotero.korex.data.SyncCoordinator
import io.github.kkokotero.korex.errors.GlobalErrorHandler
import io.github.kkokotero.korex.intents.AndroidIntentLauncher
import io.github.kkokotero.korex.intents.IntentActions
import io.github.kkokotero.korex.intents.IntentLauncher
import io.github.kkokotero.korex.lifecycle.AutoCancelJob
import io.github.kkokotero.korex.lifecycle.AutoCancelJobObserver
import io.github.kkokotero.korex.lifecycle.LifecycleTask
import io.github.kkokotero.korex.lifecycle.LifecycleTaskGroup
import io.github.kkokotero.korex.network.ApiResult
import io.github.kkokotero.korex.network.HttpClient
import io.github.kkokotero.korex.network.NetworkRequest
import io.github.kkokotero.korex.network.safeRequest
import io.github.kkokotero.korex.notifications.NotificationCenter
import io.github.kkokotero.korex.notifications.notifications
import io.github.kkokotero.korex.sample.data.GreetingRepository
import io.github.kkokotero.korex.sample.data.HomeSignalStore
import io.github.kkokotero.korex.sample.domain.HomeEvent
import io.github.kkokotero.korex.sample.domain.HomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeController(
    private val context: Context,
    private val state: ObservableValue<HomeUiState>,
    private val logger: Logger,
    private val config: io.github.kkokotero.korex.config.Config,
    private val greetingRepository: GreetingRepository,
    private val greetingProvider: GreetingProvider,
    private val greetingResolver: GreetingResolver,
    private val greetingContract: GreetingContract,
    private val titleTransformer: TitleTransformer,
    private val messageMapper: MessageMapper,
    private val homeOperation: HomeOperation,
    private val retry: Retry,
    private val validator: Validator<String>,
    private val permissionManager: PermissionManager,
    private val notificationCenter: NotificationCenter,
    private val intentLauncher: IntentLauncher,
    private val signalStore: HomeSignalStore,
    private val localData: InMemoryLocalDataSource<String, String>,
    private val syncCoordinator: SyncCoordinator<String, String>,
    private val eventBus: EventBus<String>,
    private val lifecycleTaskGroup: LifecycleTaskGroup,
    private val autoCancelJob: AutoCancelJob,
    private val globalErrorHandler: GlobalErrorHandler,
    private val capability: SampleCapability,
    private val pipelineGate: Gate<HomeUiState>,
    private val reducer: HomeReducer,
    private val stateObserver: StateObserver,
    private val networkHandler: NetworkAwareHandler,
    private val trace: HomeTrace,
) {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        state.observe { stateObserver.observe(it) }
        eventBus.subscribe { trace.record("event-bus:$it") }
        lifecycleTaskGroup.add(LifecycleTask { scope ->
            scope.launch {
                while (isActive) {
                    delay(10_000)
                    trace.record("heartbeat:${config.get<String>("app_name")}")
                }
            }
        })
        lifecycleTaskGroup.start(autoCancelJob.scope)
    }

    fun loadGreeting() {
        val current = state.value
        val greeting = greetingProvider.get()
        val resolved = runBlocking { greetingContract.resolve(greeting) }
        val transformed = titleTransformer.transform(resolved)
        update(current.copy(title = transformed, subtitle = greeting, errorMessage = null))
        trace.record("greeting loaded from ${greetingRepository.greeting()}")
    }

    fun runPipeline() {
        val current = state.value
        val allowed = runBlocking { pipelineGate.open(current) }
        if (!allowed) {
            update(current.copy(errorMessage = "Pipeline disabled"))
            return
        }

        val result = pipeline<String>()
            .then("trim") { it.trim() }
            .then("titleCase") { titleTransformer.transform(it) }
            .then("decorate") { "KoreX: $it" }
            .run("  demo pipeline  ")

        result
            .onSuccess { value ->
                val signal = signalStore.push(value)
                update(state.value.copy(pipelineResult = value, lastSignal = signal.value, snapshotVersion = signal.emittedAt, errorMessage = null))
                eventBus.publish("pipeline:$value")
            }
            .onFailure { error ->
                update(state.value.copy(errorMessage = error.message))
                globalErrorHandler.handle(error)
            }
    }

    fun validateInput() {
        val input = greetingRepository.greeting()
        val valid = greetingContract.validate(input) && validator.validate(input) is io.github.kkokotero.korex.core.ValidationResult.Valid
        update(state.value.copy(validationResult = if (valid) "Valid" else "Invalid"))
    }

    fun runNetwork() {
        val result = safeRequest {
            runBlocking {
                when (val apiResult = homeOperation.execute("hello")) {
                    is ApiResult.Success -> apiResult.value
                    is ApiResult.Failure -> throw apiResult.error.cause ?: IllegalStateException(apiResult.error.message)
                    is ApiResult.Offline -> throw IllegalStateException("Offline")
                }
            }
        }

        val mapped = messageMapper.map(result)
        update(state.value.copy(networkResult = mapped))
        runBlocking { networkHandler.handle(HomeEvent.RunNetwork) }
    }

    fun runRetry() {
        val result = retry.run {
            val next = state.value.attempts + 1
            update(state.value.copy(attempts = next))
            check(next >= 2) { "First attempt fails by design" }
            "Recovered on attempt $next"
        }

        result
            .onSuccess { value -> update(state.value.copy(networkResult = value)) }
            .onFailure { error ->
                update(state.value.copy(errorMessage = error.message))
                globalErrorHandler.handle(error)
            }
    }

    fun syncCache() {
        val value = syncCoordinator.sync("greeting")
        val message = value.fold(
            onSuccess = { "Cached: $it" },
            onFailure = { "Cache error: ${it.message}" },
        )
        update(state.value.copy(cachedValue = message))
    }

    fun showNotification() {
        update(state.value.copy(notificationPermissionState = "Requesting"))
        permissionManager.request(listOf(Permissions.Notifications)) {
            granted { permission ->
                update(
                    state.value.copy(
                        notificationPermissionState = "Granted",
                        notificationResult = "Notification posted",
                    ),
                )
                notificationCenter.channel("sample", permission.name).show("Demo notification from KoreX")
            }
            denied {
                update(
                    state.value.copy(
                        notificationPermissionState = "Denied",
                        notificationResult = "Permission denied",
                        errorMessage = "Notification permission denied",
                    ),
                )
            }
            permanentlyDenied {
                update(
                    state.value.copy(
                        notificationPermissionState = "Permanently denied",
                        notificationResult = "Open app settings to enable notifications",
                        errorMessage = "Notification permission permanently denied",
                    ),
                )
            }
        }
    }

    fun openUrl() {
        intentLauncher.launch(IntentActions.openUrl("https://github.com/kkokotero/KoreX"))
    }

    fun share() {
        intentLauncher.launch(IntentActions.shareContent("KoreX", "Built with KoreX"))
    }

    fun email() {
        intentLauncher.launch(IntentActions.sendEmail("dev@example.com", "KoreX sample", "Hello from the sample app"))
    }

    fun callPhone() {
        intentLauncher.launch(IntentActions.callPhone("+123456789"))
    }

    fun clearError() {
        update(reducer.reduce(state.value, HomeEvent.ResetError))
    }

    fun openSettings() {
        intentLauncher.launch(IntentActions.openSettings(context))
    }

    fun onNotificationPermissionResult(granted: Boolean) {
        update(
            state.value.copy(
                notificationPermissionState = if (granted) "Granted" else "Denied",
                notificationResult = if (granted) "Permission granted" else "Permission denied",
                errorMessage = if (granted) null else "Notification permission denied",
            ),
        )
    }

    private fun update(next: HomeUiState) {
        state.value = next.copy(snapshotVersion = System.currentTimeMillis())
    }
}
