package io.github.kkokotero.korex.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kkokotero.korex.sample.domain.HomeUiState

@Composable
fun HomeScreen(
    state: HomeUiState,
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
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = state.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = state.subtitle, style = MaterialTheme.typography.bodyMedium)

            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Attempts: ${state.attempts}")
                    Text(text = "Pipeline: ${state.pipelineResult}")
                    Text(text = "Validation: ${state.validationResult}")
                    Text(text = "Network: ${state.networkResult}")
                    Text(text = "Cache: ${state.cachedValue}")
                    Text(text = "Notif permission: ${state.notificationPermissionState}")
                    Text(text = "Notif result: ${state.notificationResult}")
                    Text(text = "Snapshot: ${state.snapshotVersion}")
                    Text(text = "Signal: ${state.lastSignal}")
                    Text(text = "Error: ${state.errorMessage ?: "None"}")
                }
            }

            Button(onClick = onLoad) { Text("Load greeting") }
            Button(onClick = onRunPipeline) { Text("Run pipeline") }
            Button(onClick = onValidate) { Text("Validate input") }
            Button(onClick = onNetwork) { Text("Run network") }
            Button(onClick = onRetry) { Text("Retry task") }
            Button(onClick = onSync) { Text("Sync cache") }
            Button(onClick = onNotify) { Text("Show notification") }
            Button(onClick = onOpenUrl) { Text("Open url") }
            Button(onClick = onShare) { Text("Share content") }
            Button(onClick = onEmail) { Text("Send email") }
            Button(onClick = onCall) { Text("Call phone") }
            Button(onClick = onSettings) { Text("Open settings") }
            Button(onClick = onClearError) { Text("Clear error") }
        }
    }
}
