package com.example.absenbnn.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
    ],
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password_hash")
    val passwordHash: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "role")
    val role: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
)
