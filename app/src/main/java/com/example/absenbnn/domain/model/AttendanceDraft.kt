package com.example.absenbnn.domain.model

data class AttendanceDraft(
    val divisionId: Long,
    val attendanceDate: String,
    val jumlah: Int,
    val hadir: Int,
    val breakdown: AttendanceBreakdown,
) {
    fun kurang(): Int = jumlah - hadir
}

