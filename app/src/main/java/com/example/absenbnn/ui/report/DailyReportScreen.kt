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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun DailyReportScreen(
    onBack: () -> Unit,
    viewModel: DailyReportViewModel = viewModel(factory = AppViewModelFactory),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rekap Harian") }) },
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
                    onValueChange = viewModel::setDate,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("YYYY-MM-DD") },
                    label = { Text("Tanggal") },
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

@Composable
internal fun SummaryCard(summary: com.example.absenbnn.domain.model.ReportSummary) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Total", style = MaterialTheme.typography.titleMedium)
            Text("Pegawai: ${summary.totalPegawai}")
            Text("Hadir: ${summary.totalHadir}")
            Text("Kurang: ${summary.totalKurang}")
            Text("Dinas: ${summary.totalDinas}, Terlambat: ${summary.totalTerlambat}, Sakit: ${summary.totalSakit}")
            Text("Cuti: ${summary.totalCuti}, Izin: ${summary.totalIzin}, Off: ${summary.totalOffDinas}, Lain: ${summary.totalLainLain}")
            Text("Persentase hadir: ${"%.2f".format(summary.persentaseHadir)}%")
        }
    }
}

@Composable
internal fun ReportRowCard(row: com.example.absenbnn.domain.model.ReportRow) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("${row.divisionCode} - ${row.divisionName}", style = MaterialTheme.typography.titleSmall)
            Text("Jumlah: ${row.jumlah}, Hadir: ${row.hadir}, Kurang: ${row.kurang}")
            Text("Dinas: ${row.dinas}, Terlambat: ${row.terlambat}, Sakit: ${row.sakit}, Cuti: ${row.cuti}")
            Text("Izin: ${row.izin}, Off: ${row.offDinas}, Lain: ${row.lainLain}")
        }
    }
}

