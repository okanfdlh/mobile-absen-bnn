package com.example.absenbnn.domain.model

data class AttendanceDaily(
    val id: Long,
    val divisionId: Long,
    val attendanceDate: String,
    val year: Int,
    val month: Int,
    val monthKey: String,
    val weekKey: String,
    val jumlah: Int,
    val hadir: Int,
    val kurang: Int,
    val breakdown: AttendanceBreakdown,
    val createdBy: Long,
    val createdAt: Long,
    val updatedAt: Long,
)

