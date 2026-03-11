package com.example.absenbnn.domain.usecase

import com.example.absenbnn.data.repository.AuthRepository
import com.example.absenbnn.domain.model.Session

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(username: String, password: String): Result<Session> {
        if (username.isBlank()) return Result.failure(IllegalArgumentException("Username wajib diisi"))
        if (password.isBlank()) return Result.failure(IllegalArgumentException("Password wajib diisi"))
        return authRepository.login(username, password)
    }
}

