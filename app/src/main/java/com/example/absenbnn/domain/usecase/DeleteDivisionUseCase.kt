package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.DivisionRepository

class DeleteDivisionUseCase(
    private val divisionRepository: DivisionRepository,
) {
    suspend fun execute(id: Long): Result<Unit> = runCatching {
        divisionRepository.delete(id)
    }
}
