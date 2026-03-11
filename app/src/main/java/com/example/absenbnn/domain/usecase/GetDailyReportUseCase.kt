package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.ReportRepository
import com.example.absenbnn.domain.model.ReportRow
import com.example.absenbnn.domain.model.ReportSummary

class GetDailyReportUseCase(
    private val reportRepository: ReportRepository,
) {
    suspend fun execute(attendanceDate: String): Pair<List<ReportRow>, ReportSummary> {
        return reportRepository.daily(attendanceDate)
    }
}

