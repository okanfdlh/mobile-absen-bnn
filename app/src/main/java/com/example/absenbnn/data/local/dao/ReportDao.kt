package com.example.absenbnn.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ReportDao {
    @Query(
        """
        SELECT
            d.id AS divisionId,
            d.code AS divisionCode,
            d.name AS divisionName,
            COALESCE(a.jumlah, 0) AS jumlah,
            COALESCE(a.hadir, 0) AS hadir,
            COALESCE(a.kurang, 0) AS kurang,
            COALESCE(a.dinas, 0) AS dinas,
            COALESCE(a.terlambat, 0) AS terlambat,
            COALESCE(a.sakit, 0) AS sakit,
            COALESCE(a.cuti, 0) AS cuti,
            COALESCE(a.izin, 0) AS izin,
            COALESCE(a.off_dinas, 0) AS offDinas,
            COALESCE(a.lain_lain, 0) AS lainLain
        FROM divisions d
        LEFT JOIN attendance_daily a
            ON a.division_id = d.id AND a.attendance_date = :attendanceDate
        WHERE d.is_active = 1
        ORDER BY d.name ASC
        """,
    )
    suspend fun dailyByDate(attendanceDate: String): List<ReportRow>

    @Query(
        """
        SELECT
            d.id AS divisionId,
            d.code AS divisionCode,
            d.name AS divisionName,
            COALESCE(w.jumlah, 0) AS jumlah,
            COALESCE(w.hadir, 0) AS hadir,
            COALESCE(w.kurang, 0) AS kurang,
            COALESCE(w.dinas, 0) AS dinas,
            COALESCE(w.terlambat, 0) AS terlambat,
            COALESCE(w.sakit, 0) AS sakit,
            COALESCE(w.cuti, 0) AS cuti,
            COALESCE(w.izin, 0) AS izin,
            COALESCE(w.offDinas, 0) AS offDinas,
            COALESCE(w.lainLain, 0) AS lainLain
        FROM divisions d
        LEFT JOIN (
            SELECT
                division_id AS divisionId,
                SUM(jumlah) AS jumlah,
                SUM(hadir) AS hadir,
                SUM(kurang) AS kurang,
                SUM(dinas) AS dinas,
                SUM(terlambat) AS terlambat,
                SUM(sakit) AS sakit,
                SUM(cuti) AS cuti,
                SUM(izin) AS izin,
                SUM(off_dinas) AS offDinas,
                SUM(lain_lain) AS lainLain
            FROM attendance_daily
            WHERE week_key = :weekKey
            GROUP BY division_id
        ) w ON w.divisionId = d.id
        WHERE d.is_active = 1
        ORDER BY d.name ASC
        """,
    )
    suspend fun weeklyByWeekKey(weekKey: String): List<ReportRow>

    @Query(
        """
        SELECT
            d.id AS divisionId,
            d.code AS divisionCode,
            d.name AS divisionName,
            COALESCE(m.jumlah, 0) AS jumlah,
            COALESCE(m.hadir, 0) AS hadir,
            COALESCE(m.kurang, 0) AS kurang,
            COALESCE(m.dinas, 0) AS dinas,
            COALESCE(m.terlambat, 0) AS terlambat,
            COALESCE(m.sakit, 0) AS sakit,
            COALESCE(m.cuti, 0) AS cuti,
            COALESCE(m.izin, 0) AS izin,
            COALESCE(m.offDinas, 0) AS offDinas,
            COALESCE(m.lainLain, 0) AS lainLain
        FROM divisions d
        LEFT JOIN (
            SELECT
                division_id AS divisionId,
                SUM(jumlah) AS jumlah,
                SUM(hadir) AS hadir,
                SUM(kurang) AS kurang,
                SUM(dinas) AS dinas,
                SUM(terlambat) AS terlambat,
                SUM(sakit) AS sakit,
                SUM(cuti) AS cuti,
                SUM(izin) AS izin,
                SUM(off_dinas) AS offDinas,
                SUM(lain_lain) AS lainLain
            FROM attendance_daily
            WHERE month_key = :monthKey
            GROUP BY division_id
        ) m ON m.divisionId = d.id
        WHERE d.is_active = 1
        ORDER BY d.name ASC
        """,
    )
    suspend fun monthlyByMonthKey(monthKey: String): List<ReportRow>

    @Query("SELECT DISTINCT week_key FROM attendance_daily ORDER BY week_key DESC")
    suspend fun listWeekHistory(): List<String>

    @Query("SELECT DISTINCT month_key FROM attendance_daily ORDER BY month_key DESC")
    suspend fun listMonthHistory(): List<String>
}

data class ReportRow(
    val divisionId: Long,
    val divisionCode: String,
    val divisionName: String,
    val jumlah: Int,
    val hadir: Int,
    val kurang: Int,
    val dinas: Int,
    val terlambat: Int,
    val sakit: Int,
    val cuti: Int,
    val izin: Int,
    val offDinas: Int,
    val lainLain: Int,
)
