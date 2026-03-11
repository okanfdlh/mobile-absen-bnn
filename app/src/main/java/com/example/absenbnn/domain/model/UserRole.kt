package com.example.absenbnn.domain.model

enum class UserRole {
    ADMIN,
    VIEWER,
    ;

    companion object {
        fun fromStorage(value: String?): UserRole {
            return when (value?.uppercase()) {
                "ADMIN" -> ADMIN
                "VIEWER" -> VIEWER
                else -> VIEWER
            }
        }
    }
}
