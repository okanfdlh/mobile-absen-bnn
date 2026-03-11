package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.DivisionRepository
import com.example.absenbnn.domain.model.Division
import kotlinx.coroutines.flow.Flow

class ObserveDivisionsUseCase(
    private val divisionRepository: DivisionRepository,
) {
    fun all(): Flow<List<Division>> = divisionRepository.observeAll()
    fun active(): Flow<List<Division>> = divisionRepository.observeActive()
}

