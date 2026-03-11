package com.example.absenbnn.domain.model

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

