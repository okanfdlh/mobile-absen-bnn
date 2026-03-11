package com.example.absenbnn.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.absenbnn.ServiceLocator
import com.example.absenbnn.ui.dashboard.DashboardScreen
import com.example.absenbnn.ui.division.DivisionManagementScreen
import com.example.absenbnn.ui.history.HistoryScreen
import com.example.absenbnn.ui.login.LoginScreen
import com.example.absenbnn.ui.report.DailyReportScreen
import com.example.absenbnn.ui.report.MonthlyReportScreen
import com.example.absenbnn.ui.report.WeeklyReportScreen
import com.example.absenbnn.ui.theme.AbsenBnnTheme
import com.example.absenbnn.ui.today.AttendanceTodayScreen
import kotlinx.coroutines.flow.first

object Routes {
    const val Splash = "splash"
    const val Login = "login"
    const val Dashboard = "dashboard"
    const val AttendanceToday = "attendance_today"
    const val ReportDaily = "report_daily"
    const val ReportWeekly = "report_weekly"
    const val ReportMonthly = "report_monthly"
    const val History = "history"
    const val Division = "division"
}

@Composable
fun AppRoot() {
    AbsenBnnTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.Splash) {
            composable(Routes.Splash) {
                LaunchedEffect(Unit) {
                    val session = ServiceLocator.sessionRepository.session.first()
                    val next = if (session == null) Routes.Login else Routes.Dashboard
                    navController.navigate(next) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
                Scaffold(topBar = { TopAppBar(title = { Text("Absen BNN") }) }) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Memuat...")
                    }
                }
            }
            composable(Routes.Login) {
                LoginScreen(
                    onLoggedIn = {
                        navController.navigate(Routes.Dashboard) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.Dashboard) {
                DashboardScreen(
                    onNavigate = { route -> navController.navigate(route) },
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Dashboard) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.AttendanceToday) {
                AttendanceTodayScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.ReportDaily) {
                DailyReportScreen(onBack = { navController.popBackStack() })
            }
            composable("${Routes.ReportWeekly}?weekKey={weekKey}") { backStackEntry ->
                val weekKey = backStackEntry.arguments?.getString("weekKey").orEmpty().ifBlank { null }
                WeeklyReportScreen(onBack = { navController.popBackStack() }, initialWeekKey = weekKey)
            }
            composable("${Routes.ReportMonthly}?monthKey={monthKey}") { backStackEntry ->
                val monthKey = backStackEntry.arguments?.getString("monthKey").orEmpty().ifBlank { null }
                MonthlyReportScreen(onBack = { navController.popBackStack() }, initialMonthKey = monthKey)
            }
            composable(Routes.History) {
                HistoryScreen(
                    onBack = { navController.popBackStack() },
                    onOpenWeek = { weekKey -> navController.navigate("${Routes.ReportWeekly}?weekKey=$weekKey") },
                    onOpenMonth = { monthKey -> navController.navigate("${Routes.ReportMonthly}?monthKey=$monthKey") },
                )
            }
            composable(Routes.Division) {
                DivisionManagementScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
