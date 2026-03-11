package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute() {
        authRepository.logout()
    }
}

