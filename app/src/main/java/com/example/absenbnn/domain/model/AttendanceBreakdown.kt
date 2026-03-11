package com.example.absenbnn.domain.model

data class AttendanceBreakdown(
    val dinas: Int,
    val terlambat: Int,
    val sakit: Int,
    val cuti: Int,
    val izin: Int,
    val offDinas: Int,
    val lainLain: Int,
    val lainLainNote: String?,
) {
    fun total(): Int = dinas + terlambat + sakit + cuti + izin + offDinas + lainLain
}

