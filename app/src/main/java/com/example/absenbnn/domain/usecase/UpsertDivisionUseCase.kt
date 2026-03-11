package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.DivisionRepository

class UpsertDivisionUseCase(
    private val divisionRepository: DivisionRepository,
) {
    suspend fun execute(id: Long?, code: String, name: String, isActive: Boolean): Result<Unit> {
        if (code.isBlank()) return Result.failure(IllegalArgumentException("Kode bidang wajib diisi"))
        if (name.isBlank()) return Result.failure(IllegalArgumentException("Nama bidang wajib diisi"))
        divisionRepository.upsert(id = id, code = code, name = name, isActive = isActive)
        return Result.success(Unit)
    }
}

