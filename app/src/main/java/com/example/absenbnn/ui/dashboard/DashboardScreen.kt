package com.example.absenbnn.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.absenbnn.domain.model.UserRole
import com.example.absenbnn.ui.AppViewModelFactory
import com.example.absenbnn.ui.Routes

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = viewModel(factory = AppViewModelFactory),
) {
    val session by viewModel.session.collectAsState()
    val role = session?.role ?: UserRole.VIEWER

    Scaffold(
        topBar = { TopAppBar(title = { Text("Dashboard") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Halo, ${session?.username ?: "-"}",
                style = MaterialTheme.typography.titleMedium,
            )

            if (role == UserRole.ADMIN) {
                DashboardMenuItem("Absen Hari Ini") { onNavigate(Routes.AttendanceToday) }
            }
            DashboardMenuItem("Rekap Harian") { onNavigate(Routes.ReportDaily) }
            DashboardMenuItem("Rekap Mingguan") { onNavigate("${Routes.ReportWeekly}?weekKey=") }
            DashboardMenuItem("Rekap Bulanan") { onNavigate("${Routes.ReportMonthly}?monthKey=") }
            DashboardMenuItem("Riwayat") { onNavigate(Routes.History) }

            if (role == UserRole.ADMIN) {
                DashboardMenuItem("Master Bidang") { onNavigate(Routes.Division) }
            }

            DashboardMenuItem("Logout") {
                viewModel.logout()
                onLogout()
            }
        }
    }
}

@Composable
private fun DashboardMenuItem(
    title: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
