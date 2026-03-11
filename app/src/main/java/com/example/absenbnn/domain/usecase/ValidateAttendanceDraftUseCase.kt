package com.example.absenbnn.domain.usecase

import com.example.absenbnn.domain.model.AttendanceDraft

class ValidateAttendanceDraftUseCase {
    fun execute(draft: AttendanceDraft): Result<Unit> {
        if (draft.divisionId <= 0) return Result.failure(IllegalArgumentException("Bidang wajib dipilih"))
        if (draft.jumlah < 0) return Result.failure(IllegalArgumentException("Jumlah pegawai tidak boleh negatif"))
        if (draft.hadir < 0) return Result.failure(IllegalArgumentException("Jumlah hadir tidak boleh negatif"))
        if (draft.hadir > draft.jumlah) return Result.failure(IllegalArgumentException("Hadir tidak boleh lebih besar dari jumlah"))

        val kurang = draft.kurang()
        if (kurang < 0) return Result.failure(IllegalArgumentException("Nilai kurang tidak valid"))

        val breakdown = draft.breakdown
        val values = listOf(
            breakdown.dinas,
            breakdown.terlambat,
            breakdown.sakit,
            breakdown.cuti,
            breakdown.izin,
            breakdown.offDinas,
            breakdown.lainLain,
        )
        if (values.any { it < 0 }) return Result.failure(IllegalArgumentException("Nilai keterangan tidak boleh negatif"))
        if (breakdown.total() != kurang) {
            return Result.failure(IllegalArgumentException("Total keterangan kurang harus sama dengan nilai kurang"))
        }

        return Result.success(Unit)
    }
}

