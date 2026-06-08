package io.github.kkokotero.korex.sample

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.github.kkokotero.korex.android.PermissionManager
import io.github.kkokotero.korex.android.PermissionRationaleChecker
import io.github.kkokotero.korex.android.PermissionRequester
import io.github.kkokotero.korex.android.permissionManager
import io.github.kkokotero.korex.sample.base.AllowPipeline
import io.github.kkokotero.korex.sample.base.GreetingContract
import io.github.kkokotero.korex.sample.base.GreetingFactory
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
import io.github.kkokotero.korex.core.ConsoleLogger
import io.github.kkokotero.korex.core.EventBus
import io.github.kkokotero.korex.core.ObservableValue
import io.github.kkokotero.korex.core.Retry
import io.github.kkokotero.korex.core.Validator
import io.github.kkokotero.korex.core.retry
import io.github.kkokotero.korex.core.validator
import io.github.kkokotero.korex.data.InMemoryLocalDataSource
import io.github.kkokotero.korex.data.SyncCoordinator
import io.github.kkokotero.korex.errors.GlobalErrorHandler
import io.github.kkokotero.korex.errors.userFacingError
import io.github.kkokotero.korex.intents.AndroidIntentLauncher
import io.github.kkokotero.korex.lifecycle.AutoCancelJob
import io.github.kkokotero.korex.lifecycle.AutoCancelJobObserver
import io.github.kkokotero.korex.lifecycle.LifecycleTaskGroup
import io.github.kkokotero.korex.network.HttpClient
import io.github.kkokotero.korex.network.NetworkRequest
import io.github.kkokotero.korex.network.networkRequest
import io.github.kkokotero.korex.notifications.NotificationCenter
import io.github.kkokotero.korex.notifications.LocalNotification
import io.github.kkokotero.korex.notifications.NotificationPublisher
import io.github.kkokotero.korex.notifications.notifications
import io.github.kkokotero.korex.sample.app.HomeController
import io.github.kkokotero.korex.sample.config.sampleConfig
import io.github.kkokotero.korex.sample.data.GreetingRepository
import io.github.kkokotero.korex.sample.data.HomeSignalStore
import io.github.kkokotero.korex.sample.domain.HomeUiState
import io.github.kkokotero.korex.sample.errors.SampleErrorMapper
import io.github.kkokotero.korex.sample.network.FakeHttpClient
import io.github.kkokotero.korex.sample.ui.HomeScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    private var pendingPermissionResult: ((Boolean) -> Unit)? = null
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        pendingPermissionResult?.invoke(granted)
        pendingPermissionResult = null
    }

    private val logger = ConsoleLogger()
    private val state = ObservableValue(HomeUiState())
    private val eventBus = EventBus<String>()
    private val retry: Retry = retry()
    private val repository = GreetingRepository()
    private val provider = GreetingProvider(repository)
    private val resolver = GreetingResolver(provider)
    private val contract = GreetingContract(resolver)
    private val transformer = TitleTransformer()
    private val mapper = MessageMapper()
    private val httpClient: HttpClient = FakeHttpClient()
    private val operation = HomeOperation(httpClient)
    private val useCase = RetryableUseCase(operation, transformer)
    private val reducer = HomeReducer()
    private val signalStore = HomeSignalStore()
    private val trace = HomeTrace(logger)
    private val observer = StateObserver(trace)
    private val networkHandler = NetworkAwareHandler(trace)
    private val appConfig by lazy { sampleConfig(this) }
    private val capability = SampleCapability(appConfig.get<Boolean>("feature_pipeline"))
    private val pipelineGate = AllowPipeline(capability.isAvailable())
    private val validator: Validator<String> = validator {
        rule("Value must be present") { it.isNotBlank() }
        rule("Value must contain KoreX") { it.contains("KoreX") }
    }
    private val localData = InMemoryLocalDataSource<String, String>()
    private val syncCoordinator = SyncCoordinator(
        local = localData,
        remote = io.github.kkokotero.korex.data.RemoteDataSource { key -> "remote:$key" },
    )
    private val permissionManager: PermissionManager = permissionManager(
        this,
        PermissionRequester { permission, onResult ->
            pendingPermissionResult = onResult
            requestPermissionLauncher.launch(permission.name)
        },
        rationaleChecker = PermissionRationaleChecker { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission.name)
        },
    )
    private val notificationCenter: NotificationCenter = notifications(
        object : NotificationPublisher {
            override fun publish(notification: LocalNotification) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                val channel = NotificationChannel(
                    notification.channel.id,
                    notification.channel.name,
                    notification.channel.importance,
                ).apply {
                    description = notification.channel.description
                }
                getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
                val built = NotificationCompat.Builder(this@MainActivity, notification.channel.id)
                    .setSmallIcon(notification.smallIcon)
                    .setContentTitle(notification.title)
                    .setContentText(notification.text)
                    .setOngoing(notification.ongoing)
                    .setAutoCancel(!notification.ongoing)
                    .build()
                NotificationManagerCompat.from(this@MainActivity).notify(notification.id, built)
            }
        },
    )
    private val intentLauncher = AndroidIntentLauncher(this)
    private val autoCancelJob = AutoCancelJob()
    private val lifecycleTaskGroup = LifecycleTaskGroup()
    private val globalErrorHandler: GlobalErrorHandler = GlobalErrorHandler { throwable ->
        val error = SampleErrorMapper().map(throwable)
        logger.log(io.github.kkokotero.korex.core.LogLevel.ERROR, error.title, error.description, error.cause)
    }

    private val controller = HomeController(
        context = this,
        state = state,
        logger = logger,
        config = appConfig,
        greetingRepository = repository,
        greetingProvider = provider,
        greetingResolver = resolver,
        greetingContract = contract,
        titleTransformer = transformer,
        messageMapper = mapper,
        homeOperation = operation,
        retry = retry,
        validator = validator,
        permissionManager = permissionManager,
        notificationCenter = notificationCenter,
        intentLauncher = intentLauncher,
        signalStore = signalStore,
        localData = localData,
        syncCoordinator = syncCoordinator,
        eventBus = eventBus,
        lifecycleTaskGroup = lifecycleTaskGroup,
        autoCancelJob = autoCancelJob,
        globalErrorHandler = globalErrorHandler,
        capability = capability,
        pipelineGate = pipelineGate,
        reducer = reducer,
        stateObserver = observer,
        networkHandler = networkHandler,
        trace = trace,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(AutoCancelJobObserver(autoCancelJob))
        setContent {
            MaterialTheme {
                SampleApp(
                    state = state,
                    onLoad = controller::loadGreeting,
                    onRunPipeline = controller::runPipeline,
                    onValidate = controller::validateInput,
                    onNetwork = controller::runNetwork,
                    onRetry = controller::runRetry,
                    onSync = controller::syncCache,
                    onNotify = controller::showNotification,
                    onOpenUrl = controller::openUrl,
                    onShare = controller::share,
                    onEmail = controller::email,
                    onCall = controller::callPhone,
                    onSettings = controller::openSettings,
                    onClearError = controller::clearError,
                )
            }
        }
    }
}

@Composable
private fun SampleApp(
    state: ObservableValue<HomeUiState>,
    onLoad: () -> Unit,
    onRunPipeline: () -> Unit,
    onValidate: () -> Unit,
    onNetwork: () -> Unit,
    onRetry: () -> Unit,
    onSync: () -> Unit,
    onNotify: () -> Unit,
    onOpenUrl: () -> Unit,
    onShare: () -> Unit,
    onEmail: () -> Unit,
    onCall: () -> Unit,
    onSettings: () -> Unit,
    onClearError: () -> Unit,
) {
    var uiState by remember(state) { mutableStateOf(state.value) }

    DisposableEffect(state) {
        val unsubscribe = state.observe { uiState = it }
        onDispose { unsubscribe() }
    }

    HomeScreen(
        state = uiState,
        onLoad = onLoad,
        onRunPipeline = onRunPipeline,
        onValidate = onValidate,
        onNetwork = onNetwork,
        onRetry = onRetry,
        onSync = onSync,
        onNotify = onNotify,
        onOpenUrl = onOpenUrl,
        onShare = onShare,
        onEmail = onEmail,
        onCall = onCall,
        onSettings = onSettings,
        onClearError = onClearError,
    )
}
