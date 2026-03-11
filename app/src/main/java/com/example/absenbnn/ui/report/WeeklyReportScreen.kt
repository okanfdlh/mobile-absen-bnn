package com.example.absenbnn.ui.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.ui.AppViewModelFactory

@Composable
fun WeeklyReportScreen(
    onBack: () -> Unit,
    initialWeekKey: String? = null,
    viewModel: WeeklyReportViewModel = viewModel(factory = AppViewModelFactory),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(initialWeekKey) {
        if (!initialWeekKey.isNullOrBlank()) {
            viewModel.setWeekKey(initialWeekKey)
            viewModel.load(initialWeekKey)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rekap Mingguan") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.key,
                    onValueChange = viewModel::setWeekKey,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("YYYY-Www") },
                    label = { Text("Week Key") },
                )
                Button(onClick = { viewModel.load(state.key) }) {
                    Text(if (state.isLoading) "..." else "Muat")
                }
            }

            state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            state.summary?.let { SummaryCard(it) }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(state.rows) { row ->
                    ReportRowCard(row)
                }
                item {
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Kembali") }
                }
            }
        }
    }
}
