package com.example.absenbnn.data.repository

import com.example.absenbnn.data.local.dao.UserDao
import com.example.absenbnn.domain.model.Session
import com.example.absenbnn.domain.model.UserRole
import com.example.absenbnn.util.PasswordHasher

class AuthRepository(
    private val userDao: UserDao,
    private val sessionRepository: SessionRepository,
) {
    suspend fun login(username: String, password: String): Result<Session> {
        val user = userDao.findByUsername(username.trim())
            ?: return Result.failure(IllegalArgumentException("Username atau password salah"))

        if (!user.isActive) {
            return Result.failure(IllegalStateException("Akun tidak aktif"))
        }

        val ok = PasswordHasher.verify(password, user.passwordHash)
        if (!ok) return Result.failure(IllegalArgumentException("Username atau password salah"))

        val session = Session(
            userId = user.id,
            username = user.username,
            role = UserRole.fromStorage(user.role),
        )
        sessionRepository.set(session)
        return Result.success(session)
    }

    suspend fun logout() {
        sessionRepository.clear()
    }
}
