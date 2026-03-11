package com.example.absenbnn.data.repository

import com.example.absenbnn.data.local.dao.ReportDao
import com.example.absenbnn.domain.model.ReportRow
import com.example.absenbnn.domain.model.ReportSummary

class ReportRepository(
    private val reportDao: ReportDao,
) {
    suspend fun daily(attendanceDate: String): Pair<List<ReportRow>, ReportSummary> {
        val rows = reportDao.dailyByDate(attendanceDate).map { it.toDomain() }
        return rows to summary(rows)
    }

    suspend fun weekly(weekKey: String): Pair<List<ReportRow>, ReportSummary> {
        val rows = reportDao.weeklyByWeekKey(weekKey).map { it.toDomain() }
        return rows to summary(rows)
    }

    suspend fun monthly(monthKey: String): Pair<List<ReportRow>, ReportSummary> {
        val rows = reportDao.monthlyByMonthKey(monthKey).map { it.toDomain() }
        return rows to summary(rows)
    }

    suspend fun listWeekHistory(): List<String> = reportDao.listWeekHistory()

    suspend fun listMonthHistory(): List<String> = reportDao.listMonthHistory()

    private fun com.example.absenbnn.data.local.dao.ReportRow.toDomain(): ReportRow {
        return ReportRow(
            divisionId = divisionId,
            divisionCode = divisionCode,
            divisionName = divisionName,
            jumlah = jumlah,
            hadir = hadir,
            kurang = kurang,
            dinas = dinas,
            terlambat = terlambat,
            sakit = sakit,
            cuti = cuti,
            izin = izin,
            offDinas = offDinas,
            lainLain = lainLain,
        )
    }

    private fun summary(rows: List<ReportRow>): ReportSummary {
        val totalPegawai = rows.sumOf { it.jumlah }
        val totalHadir = rows.sumOf { it.hadir }
        val totalKurang = rows.sumOf { it.kurang }
        val totalDinas = rows.sumOf { it.dinas }
        val totalTerlambat = rows.sumOf { it.terlambat }
        val totalSakit = rows.sumOf { it.sakit }
        val totalCuti = rows.sumOf { it.cuti }
        val totalIzin = rows.sumOf { it.izin }
        val totalOffDinas = rows.sumOf { it.offDinas }
        val totalLainLain = rows.sumOf { it.lainLain }
        val persentaseHadir = if (totalPegawai <= 0) 0.0 else (totalHadir.toDouble() / totalPegawai.toDouble()) * 100.0
        return ReportSummary(
            totalPegawai = totalPegawai,
            totalHadir = totalHadir,
            totalKurang = totalKurang,
            totalDinas = totalDinas,
            totalTerlambat = totalTerlambat,
            totalSakit = totalSakit,
            totalCuti = totalCuti,
            totalIzin = totalIzin,
            totalOffDinas = totalOffDinas,
            totalLainLain = totalLainLain,
            persentaseHadir = persentaseHadir,
        )
    }
}
