package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.SessionRepository
import com.example.absenbnn.domain.model.Session
import kotlinx.coroutines.flow.Flow

class ObserveSessionUseCase(
    private val sessionRepository: SessionRepository,
) {
    fun execute(): Flow<Session?> = sessionRepository.session
}

