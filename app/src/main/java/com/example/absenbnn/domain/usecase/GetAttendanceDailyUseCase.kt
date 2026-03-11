package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.AttendanceRepository
import com.example.absenbnn.domain.model.AttendanceDaily

class GetAttendanceDailyUseCase(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend fun execute(divisionId: Long, attendanceDate: String): AttendanceDaily? {
        return attendanceRepository.findByDivisionAndDate(divisionId, attendanceDate)
    }
}

