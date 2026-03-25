package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.AttendanceRepository

class DeleteAttendanceDailyUseCase(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend fun execute(id: Long): Result<Unit> = runCatching {
        attendanceRepository.deleteById(id)
    }
}
