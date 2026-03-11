package com.example.absenbnn.domain.model

data class Session(
    val userId: Long,
    val username: String,
    val role: UserRole,
)

