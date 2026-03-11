package com.example.absenbnn.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.ui.AppViewModelFactory

@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    onOpenWeek: (String) -> Unit,
    onOpenMonth: (String) -> Unit,
    viewModel: HistoryViewModel = viewModel(factory = AppViewModelFactory),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Riwayat") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(onClick = viewModel::refresh, modifier = Modifier.fillMaxWidth()) {
                Text(if (state.isLoading) "Memuat..." else "Refresh")
            }

            Text("Riwayat Minggu", style = MaterialTheme.typography.titleMedium)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(state.weeks) { key ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenWeek(key) },
                    ) {
                        Text(key, modifier = Modifier.padding(16.dp))
                    }
                }
            }

            Text("Riwayat Bulan", style = MaterialTheme.typography.titleMedium)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(state.months) { key ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenMonth(key) },
                    ) {
                        Text(key, modifier = Modifier.padding(16.dp))
                    }
                }
                item {
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Kembali") }
                }
            }
        }
    }
}

