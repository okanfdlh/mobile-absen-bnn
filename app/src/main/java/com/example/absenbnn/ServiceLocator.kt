package com.example.absenbnn

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.absenbnn.data.local.database.AppDatabase
import com.example.absenbnn.data.repository.AttendanceRepository
import com.example.absenbnn.data.repository.AuthRepository
import com.example.absenbnn.data.repository.DivisionRepository
import com.example.absenbnn.data.repository.ReportRepository
import com.example.absenbnn.data.repository.SessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object ServiceLocator {
    private lateinit var appContext: Context

    private val appScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    val database: AppDatabase by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "absenbnn.db")
            .addCallback(AppDatabase.seedCallback())
            .build()
    }

    val sessionDataStore by lazy {
        PreferenceDataStoreFactory.create(
            scope = appScope,
            produceFile = { appContext.preferencesDataStoreFile("session.preferences_pb") },
        )
    }

    val sessionRepository: SessionRepository by lazy {
        SessionRepository(sessionDataStore)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(database.userDao(), sessionRepository)
    }

    val divisionRepository: DivisionRepository by lazy {
        DivisionRepository(database.divisionDao())
    }

    val attendanceRepository: AttendanceRepository by lazy {
        AttendanceRepository(database.attendanceDao())
    }

    val reportRepository: ReportRepository by lazy {
        ReportRepository(database.reportDao())
    }

    fun init(context: Context) {
        appContext = context.applicationContext
    }
}
