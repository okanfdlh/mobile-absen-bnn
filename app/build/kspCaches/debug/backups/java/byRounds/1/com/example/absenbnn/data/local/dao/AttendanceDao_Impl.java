package com.example.absenbnn.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.absenbnn.data.local.entity.AttendanceDailyEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AttendanceDao_Impl implements AttendanceDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final EntityUpsertionAdapter<AttendanceDailyEntity> __upsertionAdapterOfAttendanceDailyEntity;

  public AttendanceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM attendance_daily WHERE id = ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfAttendanceDailyEntity = new EntityUpsertionAdapter<AttendanceDailyEntity>(new EntityInsertionAdapter<AttendanceDailyEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `attendance_daily` (`id`,`division_id`,`attendance_date`,`year`,`month`,`month_key`,`week_key`,`jumlah`,`hadir`,`kurang`,`dinas`,`terlambat`,`sakit`,`cuti`,`izin`,`off_dinas`,`lain_lain`,`lain_lain_note`,`created_by`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AttendanceDailyEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDivisionId());
        statement.bindString(3, entity.getAttendanceDate());
        statement.bindLong(4, entity.getYear());
        statement.bindLong(5, entity.getMonth());
        statement.bindString(6, entity.getMonthKey());
        statement.bindString(7, entity.getWeekKey());
        statement.bindLong(8, entity.getJumlah());
        statement.bindLong(9, entity.getHadir());
        statement.bindLong(10, entity.getKurang());
        statement.bindLong(11, entity.getDinas());
        statement.bindLong(12, entity.getTerlambat());
        statement.bindLong(13, entity.getSakit());
        statement.bindLong(14, entity.getCuti());
        statement.bindLong(15, entity.getIzin());
        statement.bindLong(16, entity.getOffDinas());
        statement.bindLong(17, entity.getLainLain());
        if (entity.getLainLainNote() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getLainLainNote());
        }
        statement.bindLong(19, entity.getCreatedBy());
        statement.bindLong(20, entity.getCreatedAt());
        statement.bindLong(21, entity.getUpdatedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<AttendanceDailyEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `attendance_daily` SET `id` = ?,`division_id` = ?,`attendance_date` = ?,`year` = ?,`month` = ?,`month_key` = ?,`week_key` = ?,`jumlah` = ?,`hadir` = ?,`kurang` = ?,`dinas` = ?,`terlambat` = ?,`sakit` = ?,`cuti` = ?,`izin` = ?,`off_dinas` = ?,`lain_lain` = ?,`lain_lain_note` = ?,`created_by` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AttendanceDailyEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDivisionId());
        statement.bindString(3, entity.getAttendanceDate());
        statement.bindLong(4, entity.getYear());
        statement.bindLong(5, entity.getMonth());
        statement.bindString(6, entity.getMonthKey());
        statement.bindString(7, entity.getWeekKey());
        statement.bindLong(8, entity.getJumlah());
        statement.bindLong(9, entity.getHadir());
        statement.bindLong(10, entity.getKurang());
        statement.bindLong(11, entity.getDinas());
        statement.bindLong(12, entity.getTerlambat());
        statement.bindLong(13, entity.getSakit());
        statement.bindLong(14, entity.getCuti());
        statement.bindLong(15, entity.getIzin());
        statement.bindLong(16, entity.getOffDinas());
        statement.bindLong(17, entity.getLainLain());
        if (entity.getLainLainNote() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getLainLainNote());
        }
        statement.bindLong(19, entity.getCreatedBy());
        statement.bindLong(20, entity.getCreatedAt());
        statement.bindLong(21, entity.getUpdatedAt());
        statement.bindLong(22, entity.getId());
      }
    });
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final AttendanceDailyEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __upsertionAdapterOfAttendanceDailyEntity.upsertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object findByDivisionAndDate(final long divisionId, final String attendanceDate,
      final Continuation<? super AttendanceDailyEntity> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM attendance_daily\n"
            + "        WHERE division_id = ? AND attendance_date = ?\n"
            + "        LIMIT 1\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, divisionId);
    _argIndex = 2;
    _statement.bindString(_argIndex, attendanceDate);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AttendanceDailyEntity>() {
      @Override
      @Nullable
      public AttendanceDailyEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDivisionId = CursorUtil.getColumnIndexOrThrow(_cursor, "division_id");
          final int _cursorIndexOfAttendanceDate = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance_date");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfMonthKey = CursorUtil.getColumnIndexOrThrow(_cursor, "month_key");
          final int _cursorIndexOfWeekKey = CursorUtil.getColumnIndexOrThrow(_cursor, "week_key");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "hadir");
          final int _cursorIndexOfKurang = CursorUtil.getColumnIndexOrThrow(_cursor, "kurang");
          final int _cursorIndexOfDinas = CursorUtil.getColumnIndexOrThrow(_cursor, "dinas");
          final int _cursorIndexOfTerlambat = CursorUtil.getColumnIndexOrThrow(_cursor, "terlambat");
          final int _cursorIndexOfSakit = CursorUtil.getColumnIndexOrThrow(_cursor, "sakit");
          final int _cursorIndexOfCuti = CursorUtil.getColumnIndexOrThrow(_cursor, "cuti");
          final int _cursorIndexOfIzin = CursorUtil.getColumnIndexOrThrow(_cursor, "izin");
          final int _cursorIndexOfOffDinas = CursorUtil.getColumnIndexOrThrow(_cursor, "off_dinas");
          final int _cursorIndexOfLainLain = CursorUtil.getColumnIndexOrThrow(_cursor, "lain_lain");
          final int _cursorIndexOfLainLainNote = CursorUtil.getColumnIndexOrThrow(_cursor, "lain_lain_note");
          final int _cursorIndexOfCreatedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "created_by");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final AttendanceDailyEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDivisionId;
            _tmpDivisionId = _cursor.getLong(_cursorIndexOfDivisionId);
            final String _tmpAttendanceDate;
            _tmpAttendanceDate = _cursor.getString(_cursorIndexOfAttendanceDate);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final String _tmpMonthKey;
            _tmpMonthKey = _cursor.getString(_cursorIndexOfMonthKey);
            final String _tmpWeekKey;
            _tmpWeekKey = _cursor.getString(_cursorIndexOfWeekKey);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final int _tmpHadir;
            _tmpHadir = _cursor.getInt(_cursorIndexOfHadir);
            final int _tmpKurang;
            _tmpKurang = _cursor.getInt(_cursorIndexOfKurang);
            final int _tmpDinas;
            _tmpDinas = _cursor.getInt(_cursorIndexOfDinas);
            final int _tmpTerlambat;
            _tmpTerlambat = _cursor.getInt(_cursorIndexOfTerlambat);
            final int _tmpSakit;
            _tmpSakit = _cursor.getInt(_cursorIndexOfSakit);
            final int _tmpCuti;
            _tmpCuti = _cursor.getInt(_cursorIndexOfCuti);
            final int _tmpIzin;
            _tmpIzin = _cursor.getInt(_cursorIndexOfIzin);
            final int _tmpOffDinas;
            _tmpOffDinas = _cursor.getInt(_cursorIndexOfOffDinas);
            final int _tmpLainLain;
            _tmpLainLain = _cursor.getInt(_cursorIndexOfLainLain);
            final String _tmpLainLainNote;
            if (_cursor.isNull(_cursorIndexOfLainLainNote)) {
              _tmpLainLainNote = null;
            } else {
              _tmpLainLainNote = _cursor.getString(_cursorIndexOfLainLainNote);
            }
            final long _tmpCreatedBy;
            _tmpCreatedBy = _cursor.getLong(_cursorIndexOfCreatedBy);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new AttendanceDailyEntity(_tmpId,_tmpDivisionId,_tmpAttendanceDate,_tmpYear,_tmpMonth,_tmpMonthKey,_tmpWeekKey,_tmpJumlah,_tmpHadir,_tmpKurang,_tmpDinas,_tmpTerlambat,_tmpSakit,_tmpCuti,_tmpIzin,_tmpOffDinas,_tmpLainLain,_tmpLainLainNote,_tmpCreatedBy,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
