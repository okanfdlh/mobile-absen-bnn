package com.example.absenbnn.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.absenbnn.domain.model.Session
import com.example.absenbnn.domain.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private val keyUserId = longPreferencesKey("user_id")
    private val keyUsername = stringPreferencesKey("username")
    private val keyRole = stringPreferencesKey("role")

    val session: Flow<Session?> = dataStore.data.map { prefs ->
        val userId = prefs[keyUserId] ?: return@map null
        val username = prefs[keyUsername] ?: return@map null
        val role = UserRole.fromStorage(prefs[keyRole])
        Session(userId = userId, username = username, role = role)
    }

    suspend fun set(session: Session) {
        dataStore.edit { prefs ->
            prefs[keyUserId] = session.userId
            prefs[keyUsername] = session.username
            prefs[keyRole] = session.role.name
        }
    }

    suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.remove(keyUserId)
            prefs.remove(keyUsername)
            prefs.remove(keyRole)
        }
    }
}
