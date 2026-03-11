package com.example.absenbnn.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.absenbnn.data.local.entity.DivisionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DivisionDao {
    @Query("SELECT * FROM divisions ORDER BY name ASC")
    fun observeAll(): Flow<List<DivisionEntity>>

    @Query("SELECT * FROM divisions WHERE is_active = 1 ORDER BY name ASC")
    fun observeActive(): Flow<List<DivisionEntity>>

    @Query("SELECT * FROM divisions WHERE is_active = 1 ORDER BY name ASC")
    suspend fun getActiveOnce(): List<DivisionEntity>

    @Upsert
    suspend fun upsert(division: DivisionEntity): Long

    @Query("SELECT COUNT(*) FROM divisions")
    suspend fun countAll(): Long

    @Query("SELECT * FROM divisions WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): DivisionEntity?
}
