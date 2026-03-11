package com.example.absenbnn.domain.model

data class ReportSummary(
    val totalPegawai: Int,
    val totalHadir: Int,
    val totalKurang: Int,
    val totalDinas: Int,
    val totalTerlambat: Int,
    val totalSakit: Int,
    val totalCuti: Int,
    val totalIzin: Int,
    val totalOffDinas: Int,
    val totalLainLain: Int,
    val persentaseHadir: Double,
)

