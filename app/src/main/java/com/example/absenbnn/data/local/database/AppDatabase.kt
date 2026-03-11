package com.example.absenbnn.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.absenbnn.data.local.dao.AttendanceDao
import com.example.absenbnn.data.local.dao.DivisionDao
import com.example.absenbnn.data.local.dao.ReportDao
import com.example.absenbnn.data.local.dao.UserDao
import com.example.absenbnn.data.local.entity.AttendanceDailyEntity
import com.example.absenbnn.data.local.entity.DivisionEntity
import com.example.absenbnn.data.local.entity.UserEntity
import com.example.absenbnn.util.PasswordHasher

@Database(
    entities = [
        UserEntity::class,
        DivisionEntity::class,
        AttendanceDailyEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun divisionDao(): DivisionDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun reportDao(): ReportDao

    companion object {
        fun seedCallback(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    val now = System.currentTimeMillis()
                    val adminHash = PasswordHasher.hash("admin123")

                    db.execSQL(
                        """
                        INSERT INTO users (username, password_hash, full_name, role, is_active, created_at, updated_at)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """.trimIndent(),
                        arrayOf("admin", adminHash, "Administrator", "ADMIN", 1, now, now),
                    )

                    val divisions = listOf(
                        "UMUM" to "Bidang Umum",
                        "SDM" to "Bidang SDM",
                        "KEU" to "Bidang Keuangan",
                        "OPS" to "Bidang Operasional",
                    )

                    for ((code, name) in divisions) {
                        db.execSQL(
                            """
                            INSERT INTO divisions (code, name, is_active, created_at, updated_at)
                            VALUES (?, ?, ?, ?, ?)
                            """.trimIndent(),
                            arrayOf(code, name, 1, now, now),
                        )
                    }
                }
            }
        }
    }
}
