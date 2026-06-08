package io.github.kkokotero.korex.sample.base

import io.github.kkokotero.korex.base.Capability
import io.github.kkokotero.korex.base.Contract
import io.github.kkokotero.korex.base.Factory
import io.github.kkokotero.korex.base.Gate
import io.github.kkokotero.korex.base.Handler
import io.github.kkokotero.korex.base.Mapper
import io.github.kkokotero.korex.base.Mutation
import io.github.kkokotero.korex.base.Observer
import io.github.kkokotero.korex.base.Operation
import io.github.kkokotero.korex.base.Policy
import io.github.kkokotero.korex.base.Provider
import io.github.kkokotero.korex.base.Reducer
import io.github.kkokotero.korex.base.Resolver
import io.github.kkokotero.korex.base.Trace
import io.github.kkokotero.korex.base.Transformer
import io.github.kkokotero.korex.base.UseCase
import io.github.kkokotero.korex.core.Logger
import io.github.kkokotero.korex.core.LogLevel
import io.github.kkokotero.korex.network.ApiResult
import io.github.kkokotero.korex.network.HttpClient
import io.github.kkokotero.korex.network.NetworkRequest
import io.github.kkokotero.korex.sample.data.GreetingRepository
import io.github.kkokotero.korex.sample.domain.HomeEvent
import io.github.kkokotero.korex.sample.domain.HomeUiState

class GreetingFactory : Factory<HomeUiState>() {
    override fun create(): HomeUiState = HomeUiState()
}

class GreetingProvider(private val repository: GreetingRepository) : Provider<String>() {
    override fun get(): String = repository.greeting()
}

class GreetingResolver(private val provider: Provider<String>) : Resolver<String, String>() {
    override suspend fun resolve(input: String): String = "${provider.get()}: ${input.trim()}"
}

class GreetingContract(private val resolver: Resolver<String, String>) : Contract<String, String>() {
    override fun validate(input: String): Boolean = input.isNotBlank()
    override suspend fun resolve(input: String): String = resolver.resolve(input)
}

class TitleTransformer : Transformer<String, String>() {
    override fun transform(input: String): String = input.replaceFirstChar { it.uppercase() }
}

class MessageMapper : Mapper<ApiResult<String>, String>() {
    override fun map(input: ApiResult<String>): String = when (input) {
        is ApiResult.Success -> input.value
        is ApiResult.Failure -> input.error.message
        is ApiResult.Offline -> "Offline"
    }
}

class AllowPipeline(private val enabled: Boolean) : Gate<HomeUiState>() {
    override suspend fun open(context: HomeUiState): Boolean = enabled && context.attempts < 5
}

class TitlePolicy : Policy<String>() {
    override fun allows(value: String): Boolean = value.isNotBlank()
}

class IncrementMutation : Mutation<HomeUiState>() {
    override suspend fun apply(current: HomeUiState): HomeUiState = current.copy(attempts = current.attempts + 1)
}

class HomeReducer : Reducer<HomeUiState, HomeEvent>() {
    override fun reduce(state: HomeUiState, input: HomeEvent): HomeUiState = when (input) {
        HomeEvent.ResetError -> state.copy(errorMessage = null)
        HomeEvent.RunPipeline,
        HomeEvent.RetryOperation,
        HomeEvent.RunValidation,
        HomeEvent.RunNetwork,
        HomeEvent.LoadGreeting -> state
    }
}

class HomeOperation(
    private val client: HttpClient,
) : Operation<String, ApiResult<String>>() {
    override suspend fun execute(input: String): ApiResult<String> {
        val request = NetworkRequest(path = input, body = mapOf("query" to input), responseType = String::class.java)
        return client.execute(request)
    }
}

class RetryableUseCase(
    private val operation: Operation<String, ApiResult<String>>,
    private val titleTransformer: Transformer<String, String>,
) : UseCase<String, HomeUiState>() {
    override suspend fun execute(input: String): HomeUiState {
        val result = operation.execute(input)
        return when (result) {
            is ApiResult.Success -> HomeUiState(networkResult = result.value, pipelineResult = titleTransformer.transform(result.value))
            is ApiResult.Failure -> HomeUiState(errorMessage = result.error.message)
            is ApiResult.Offline -> HomeUiState(networkResult = "Offline")
        }
    }
}

class HomeTrace(private val logger: Logger) : Trace<String>() {
    override fun record(value: String) {
        logger.log(LogLevel.INFO, "KoreX", value, null)
    }
}

class StateObserver(private val trace: Trace<String>) : Observer<HomeUiState>() {
    override fun observe(value: HomeUiState) {
        trace.record("state=${value.title}/${value.attempts}/${value.pipelineResult}")
    }
}

class NetworkAwareHandler(private val trace: Trace<String>) : Handler<HomeEvent>() {
    override suspend fun handle(input: HomeEvent) {
        trace.record("event=$input")
    }
}

class SampleCapability(private val enabled: Boolean) : Capability() {
    override fun isAvailable(): Boolean = enabled
}
