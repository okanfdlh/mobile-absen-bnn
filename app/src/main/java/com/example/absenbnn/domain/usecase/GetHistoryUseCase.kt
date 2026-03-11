package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.ReportRepository

class GetHistoryUseCase(
    private val reportRepository: ReportRepository,
) {
    suspend fun weeks(): List<String> = reportRepository.listWeekHistory()
    suspend fun months(): List<String> = reportRepository.listMonthHistory()
}

