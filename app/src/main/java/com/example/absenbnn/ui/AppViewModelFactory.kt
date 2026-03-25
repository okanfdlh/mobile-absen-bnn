package com.example.absenbnn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.absenbnn.ServiceLocator
import com.example.absenbnn.domain.usecase.DeleteAttendanceDailyUseCase
import com.example.absenbnn.domain.usecase.DeleteDivisionUseCase
import com.example.absenbnn.domain.usecase.GetAttendanceDailyUseCase
import com.example.absenbnn.domain.usecase.GetDailyReportUseCase
import com.example.absenbnn.domain.usecase.GetHistoryUseCase
import com.example.absenbnn.domain.usecase.GetMonthlyReportUseCase
import com.example.absenbnn.domain.usecase.GetWeeklyReportUseCase
import com.example.absenbnn.domain.usecase.LoginUseCase
import com.example.absenbnn.domain.usecase.LogoutUseCase
import com.example.absenbnn.domain.usecase.ObserveDivisionsUseCase
import com.example.absenbnn.domain.usecase.ObserveSessionUseCase
import com.example.absenbnn.domain.usecase.UpsertAttendanceDailyUseCase
import com.example.absenbnn.domain.usecase.UpsertDivisionUseCase
import com.example.absenbnn.domain.usecase.ValidateAttendanceDraftUseCase
import com.example.absenbnn.ui.dashboard.DashboardViewModel
import com.example.absenbnn.ui.division.DivisionViewModel
import com.example.absenbnn.ui.history.HistoryViewModel
import com.example.absenbnn.ui.login.LoginViewModel
import com.example.absenbnn.ui.report.DailyReportViewModel
import com.example.absenbnn.ui.report.MonthlyReportViewModel
import com.example.absenbnn.ui.report.WeeklyReportViewModel
import com.example.absenbnn.ui.today.AttendanceTodayViewModel

object AppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val observeSession = ObserveSessionUseCase(ServiceLocator.sessionRepository)
        val observeDivisions = ObserveDivisionsUseCase(ServiceLocator.divisionRepository)
        val validateAttendance = ValidateAttendanceDraftUseCase()

        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(LoginUseCase(ServiceLocator.authRepository)) as T

            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->
                DashboardViewModel(observeSession, LogoutUseCase(ServiceLocator.authRepository)) as T

            modelClass.isAssignableFrom(AttendanceTodayViewModel::class.java) ->
                AttendanceTodayViewModel(
                    observeSession = observeSession,
                    observeDivisions = observeDivisions,
                    getAttendanceDailyUseCase = GetAttendanceDailyUseCase(ServiceLocator.attendanceRepository),
                    upsertAttendanceDailyUseCase = UpsertAttendanceDailyUseCase(
                        ServiceLocator.attendanceRepository,
                        validateAttendance,
                    ),
                    deleteAttendanceDailyUseCase = DeleteAttendanceDailyUseCase(ServiceLocator.attendanceRepository),
                ) as T

            modelClass.isAssignableFrom(DailyReportViewModel::class.java) ->
                DailyReportViewModel(GetDailyReportUseCase(ServiceLocator.reportRepository)) as T

            modelClass.isAssignableFrom(WeeklyReportViewModel::class.java) ->
                WeeklyReportViewModel(GetWeeklyReportUseCase(ServiceLocator.reportRepository)) as T

            modelClass.isAssignableFrom(MonthlyReportViewModel::class.java) ->
                MonthlyReportViewModel(GetMonthlyReportUseCase(ServiceLocator.reportRepository)) as T

            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(GetHistoryUseCase(ServiceLocator.reportRepository)) as T

            modelClass.isAssignableFrom(DivisionViewModel::class.java) ->
                DivisionViewModel(
                    observeDivisions,
                    UpsertDivisionUseCase(ServiceLocator.divisionRepository),
                    DeleteDivisionUseCase(ServiceLocator.divisionRepository),
                ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
