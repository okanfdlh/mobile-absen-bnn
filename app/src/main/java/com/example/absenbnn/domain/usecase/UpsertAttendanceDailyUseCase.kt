package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.local.entity.AttendanceDailyEntity
import com.example.absenbnn.data.repository.AttendanceRepository
import com.example.absenbnn.domain.model.AttendanceDraft
import com.example.absenbnn.util.DateKeys

class UpsertAttendanceDailyUseCase(
    private val attendanceRepository: AttendanceRepository,
    private val validateAttendanceDraftUseCase: ValidateAttendanceDraftUseCase,
) {
    suspend fun execute(draft: AttendanceDraft, currentUserId: Long): Result<Long> {
        validateAttendanceDraftUseCase.execute(draft).getOrElse { return Result.failure(it) }

        val existing = attendanceRepository.findByDivisionAndDate(draft.divisionId, draft.attendanceDate)
        val now = System.currentTimeMillis()
        val date = DateKeys.parseDate(draft.attendanceDate)
        val kurang = draft.kurang()

        val entity = AttendanceDailyEntity(
            id = existing?.id ?: 0,
            divisionId = draft.divisionId,
            attendanceDate = draft.attendanceDate,
            year = DateKeys.year(date),
            month = DateKeys.monthNumber(date),
            monthKey = DateKeys.monthKey(date),
            weekKey = DateKeys.weekKeyIso(date),
            jumlah = draft.jumlah,
            hadir = draft.hadir,
            kurang = kurang,
            dinas = draft.breakdown.dinas,
            terlambat = draft.breakdown.terlambat,
            sakit = draft.breakdown.sakit,
            cuti = draft.breakdown.cuti,
            izin = draft.breakdown.izin,
            offDinas = draft.breakdown.offDinas,
            lainLain = draft.breakdown.lainLain,
            lainLainNote = draft.breakdown.lainLainNote?.takeIf { it.isNotBlank() },
            createdBy = existing?.createdBy ?: currentUserId,
            createdAt = existing?.createdAt ?: now,
            updatedAt = now,
        )

        val insertedIdOrIgnored = attendanceRepository.upsert(entity)
        val finalId = existing?.id ?: insertedIdOrIgnored
        return Result.success(finalId)
    }
}
