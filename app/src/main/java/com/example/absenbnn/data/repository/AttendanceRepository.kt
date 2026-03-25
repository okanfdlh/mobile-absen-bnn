package com.example.absenbnn.data.repository

import com.example.absenbnn.data.local.dao.AttendanceDao
import com.example.absenbnn.data.local.entity.AttendanceDailyEntity
import com.example.absenbnn.domain.model.AttendanceBreakdown
import com.example.absenbnn.domain.model.AttendanceDaily

class AttendanceRepository(
    private val attendanceDao: AttendanceDao,
) {
    suspend fun findByDivisionAndDate(divisionId: Long, attendanceDate: String): AttendanceDaily? {
        return attendanceDao.findByDivisionAndDate(divisionId, attendanceDate)?.toDomain()
    }

    suspend fun upsert(entity: AttendanceDailyEntity): Long {
        return attendanceDao.upsert(entity)
    }

    suspend fun deleteById(id: Long) {
        attendanceDao.deleteById(id)
    }

    private fun AttendanceDailyEntity.toDomain(): AttendanceDaily {
        return AttendanceDaily(
            id = id,
            divisionId = divisionId,
            attendanceDate = attendanceDate,
            year = year,
            month = month,
            monthKey = monthKey,
            weekKey = weekKey,
            jumlah = jumlah,
            hadir = hadir,
            kurang = kurang,
            breakdown = AttendanceBreakdown(
                dinas = dinas,
                terlambat = terlambat,
                sakit = sakit,
                cuti = cuti,
                izin = izin,
                offDinas = offDinas,
                lainLain = lainLain,
                lainLainNote = lainLainNote,
            ),
            createdBy = createdBy,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
