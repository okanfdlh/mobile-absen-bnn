package com.example.absenbnn.data.repository

import com.example.absenbnn.data.local.dao.DivisionDao
import com.example.absenbnn.data.local.entity.DivisionEntity
import com.example.absenbnn.domain.model.Division
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DivisionRepository(
    private val divisionDao: DivisionDao,
) {
    fun observeAll(): Flow<List<Division>> = divisionDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    fun observeActive(): Flow<List<Division>> = divisionDao.observeActive().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getActiveOnce(): List<Division> = divisionDao.getActiveOnce().map { it.toDomain() }

    suspend fun upsert(id: Long?, code: String, name: String, isActive: Boolean) {
        val now = System.currentTimeMillis()
        val existing = id?.let { divisionDao.findById(it) }
        val entity = DivisionEntity(
            id = id ?: 0,
            code = code.trim(),
            name = name.trim(),
            isActive = isActive,
            createdAt = existing?.createdAt ?: now,
            updatedAt = now,
        )
        divisionDao.upsert(entity)
    }

    private fun DivisionEntity.toDomain(): Division {
        return Division(id = id, code = code, name = name, isActive = isActive)
    }
}
