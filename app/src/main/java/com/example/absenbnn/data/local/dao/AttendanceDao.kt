package com.example.absenbnn.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.absenbnn.data.local.entity.AttendanceDailyEntity

@Dao
interface AttendanceDao {
    @Query(
        """
        SELECT * FROM attendance_daily
        WHERE division_id = :divisionId AND attendance_date = :attendanceDate
        LIMIT 1
        """,
    )
    suspend fun findByDivisionAndDate(divisionId: Long, attendanceDate: String): AttendanceDailyEntity?

    @Upsert
    suspend fun upsert(entity: AttendanceDailyEntity): Long

    @Query("DELETE FROM attendance_daily WHERE id = :id")
    suspend fun deleteById(id: Long)
}
