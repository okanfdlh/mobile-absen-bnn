package com.example.absenbnn.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance_daily",
    foreignKeys = [
        ForeignKey(
            entity = DivisionEntity::class,
            parentColumns = ["id"],
            childColumns = ["division_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["created_by"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["division_id", "attendance_date"], unique = true),
        Index(value = ["week_key"]),
        Index(value = ["month_key"]),
    ],
)
data class AttendanceDailyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "division_id")
    val divisionId: Long,
    @ColumnInfo(name = "attendance_date")
    val attendanceDate: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "month")
    val month: Int,
    @ColumnInfo(name = "month_key")
    val monthKey: String,
    @ColumnInfo(name = "week_key")
    val weekKey: String,
    @ColumnInfo(name = "jumlah")
    val jumlah: Int,
    @ColumnInfo(name = "hadir")
    val hadir: Int,
    @ColumnInfo(name = "kurang")
    val kurang: Int,
    @ColumnInfo(name = "dinas")
    val dinas: Int,
    @ColumnInfo(name = "terlambat")
    val terlambat: Int,
    @ColumnInfo(name = "sakit")
    val sakit: Int,
    @ColumnInfo(name = "cuti")
    val cuti: Int,
    @ColumnInfo(name = "izin")
    val izin: Int,
    @ColumnInfo(name = "off_dinas")
    val offDinas: Int,
    @ColumnInfo(name = "lain_lain")
    val lainLain: Int,
    @ColumnInfo(name = "lain_lain_note")
    val lainLainNote: String?,
    @ColumnInfo(name = "created_by")
    val createdBy: Long,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
)
