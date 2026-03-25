package com.example.absenbnn.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public Object dailyByDate(final String attendanceDate,
      final Continuation<? super List<ReportRow>> $completion) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            d.id AS divisionId,\n"
            + "            d.code AS divisionCode,\n"
            + "            d.name AS divisionName,\n"
            + "            COALESCE(a.jumlah, 0) AS jumlah,\n"
            + "            COALESCE(a.hadir, 0) AS hadir,\n"
            + "            COALESCE(a.kurang, 0) AS kurang,\n"
            + "            COALESCE(a.dinas, 0) AS dinas,\n"
            + "            COALESCE(a.terlambat, 0) AS terlambat,\n"
            + "            COALESCE(a.sakit, 0) AS sakit,\n"
            + "            COALESCE(a.cuti, 0) AS cuti,\n"
            + "            COALESCE(a.izin, 0) AS izin,\n"
            + "            COALESCE(a.off_dinas, 0) AS offDinas,\n"
            + "            COALESCE(a.lain_lain, 0) AS lainLain\n"
            + "        FROM divisions d\n"
            + "        LEFT JOIN attendance_daily a\n"
            + "            ON a.division_id = d.id AND a.attendance_date = ?\n"
            + "        WHERE d.is_active = 1\n"
            + "        ORDER BY d.name ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, attendanceDate);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ReportRow>>() {
      @Override
      @NonNull
      public List<ReportRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDivisionId = 0;
          final int _cursorIndexOfDivisionCode = 1;
          final int _cursorIndexOfDivisionName = 2;
          final int _cursorIndexOfJumlah = 3;
          final int _cursorIndexOfHadir = 4;
          final int _cursorIndexOfKurang = 5;
          final int _cursorIndexOfDinas = 6;
          final int _cursorIndexOfTerlambat = 7;
          final int _cursorIndexOfSakit = 8;
          final int _cursorIndexOfCuti = 9;
          final int _cursorIndexOfIzin = 10;
          final int _cursorIndexOfOffDinas = 11;
          final int _cursorIndexOfLainLain = 12;
          final List<ReportRow> _result = new ArrayList<ReportRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportRow _item;
            final long _tmpDivisionId;
            _tmpDivisionId = _cursor.getLong(_cursorIndexOfDivisionId);
            final String _tmpDivisionCode;
            _tmpDivisionCode = _cursor.getString(_cursorIndexOfDivisionCode);
            final String _tmpDivisionName;
            _tmpDivisionName = _cursor.getString(_cursorIndexOfDivisionName);
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
            _item = new ReportRow(_tmpDivisionId,_tmpDivisionCode,_tmpDivisionName,_tmpJumlah,_tmpHadir,_tmpKurang,_tmpDinas,_tmpTerlambat,_tmpSakit,_tmpCuti,_tmpIzin,_tmpOffDinas,_tmpLainLain);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object weeklyByWeekKey(final String weekKey,
      final Continuation<? super List<ReportRow>> $completion) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            d.id AS divisionId,\n"
            + "            d.code AS divisionCode,\n"
            + "            d.name AS divisionName,\n"
            + "            COALESCE(w.jumlah, 0) AS jumlah,\n"
            + "            COALESCE(w.hadir, 0) AS hadir,\n"
            + "            COALESCE(w.kurang, 0) AS kurang,\n"
            + "            COALESCE(w.dinas, 0) AS dinas,\n"
            + "            COALESCE(w.terlambat, 0) AS terlambat,\n"
            + "            COALESCE(w.sakit, 0) AS sakit,\n"
            + "            COALESCE(w.cuti, 0) AS cuti,\n"
            + "            COALESCE(w.izin, 0) AS izin,\n"
            + "            COALESCE(w.offDinas, 0) AS offDinas,\n"
            + "            COALESCE(w.lainLain, 0) AS lainLain\n"
            + "        FROM divisions d\n"
            + "        LEFT JOIN (\n"
            + "            SELECT\n"
            + "                division_id AS divisionId,\n"
            + "                SUM(jumlah) AS jumlah,\n"
            + "                SUM(hadir) AS hadir,\n"
            + "                SUM(kurang) AS kurang,\n"
            + "                SUM(dinas) AS dinas,\n"
            + "                SUM(terlambat) AS terlambat,\n"
            + "                SUM(sakit) AS sakit,\n"
            + "                SUM(cuti) AS cuti,\n"
            + "                SUM(izin) AS izin,\n"
            + "                SUM(off_dinas) AS offDinas,\n"
            + "                SUM(lain_lain) AS lainLain\n"
            + "            FROM attendance_daily\n"
            + "            WHERE week_key = ?\n"
            + "            GROUP BY division_id\n"
            + "        ) w ON w.divisionId = d.id\n"
            + "        WHERE d.is_active = 1\n"
            + "        ORDER BY d.name ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, weekKey);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ReportRow>>() {
      @Override
      @NonNull
      public List<ReportRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDivisionId = 0;
          final int _cursorIndexOfDivisionCode = 1;
          final int _cursorIndexOfDivisionName = 2;
          final int _cursorIndexOfJumlah = 3;
          final int _cursorIndexOfHadir = 4;
          final int _cursorIndexOfKurang = 5;
          final int _cursorIndexOfDinas = 6;
          final int _cursorIndexOfTerlambat = 7;
          final int _cursorIndexOfSakit = 8;
          final int _cursorIndexOfCuti = 9;
          final int _cursorIndexOfIzin = 10;
          final int _cursorIndexOfOffDinas = 11;
          final int _cursorIndexOfLainLain = 12;
          final List<ReportRow> _result = new ArrayList<ReportRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportRow _item;
            final long _tmpDivisionId;
            _tmpDivisionId = _cursor.getLong(_cursorIndexOfDivisionId);
            final String _tmpDivisionCode;
            _tmpDivisionCode = _cursor.getString(_cursorIndexOfDivisionCode);
            final String _tmpDivisionName;
            _tmpDivisionName = _cursor.getString(_cursorIndexOfDivisionName);
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
            _item = new ReportRow(_tmpDivisionId,_tmpDivisionCode,_tmpDivisionName,_tmpJumlah,_tmpHadir,_tmpKurang,_tmpDinas,_tmpTerlambat,_tmpSakit,_tmpCuti,_tmpIzin,_tmpOffDinas,_tmpLainLain);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object monthlyByMonthKey(final String monthKey,
      final Continuation<? super List<ReportRow>> $completion) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            d.id AS divisionId,\n"
            + "            d.code AS divisionCode,\n"
            + "            d.name AS divisionName,\n"
            + "            COALESCE(m.jumlah, 0) AS jumlah,\n"
            + "            COALESCE(m.hadir, 0) AS hadir,\n"
            + "            COALESCE(m.kurang, 0) AS kurang,\n"
            + "            COALESCE(m.dinas, 0) AS dinas,\n"
            + "            COALESCE(m.terlambat, 0) AS terlambat,\n"
            + "            COALESCE(m.sakit, 0) AS sakit,\n"
            + "            COALESCE(m.cuti, 0) AS cuti,\n"
            + "            COALESCE(m.izin, 0) AS izin,\n"
            + "            COALESCE(m.offDinas, 0) AS offDinas,\n"
            + "            COALESCE(m.lainLain, 0) AS lainLain\n"
            + "        FROM divisions d\n"
            + "        LEFT JOIN (\n"
            + "            SELECT\n"
            + "                division_id AS divisionId,\n"
            + "                SUM(jumlah) AS jumlah,\n"
            + "                SUM(hadir) AS hadir,\n"
            + "                SUM(kurang) AS kurang,\n"
            + "                SUM(dinas) AS dinas,\n"
            + "                SUM(terlambat) AS terlambat,\n"
            + "                SUM(sakit) AS sakit,\n"
            + "                SUM(cuti) AS cuti,\n"
            + "                SUM(izin) AS izin,\n"
            + "                SUM(off_dinas) AS offDinas,\n"
            + "                SUM(lain_lain) AS lainLain\n"
            + "            FROM attendance_daily\n"
            + "            WHERE month_key = ?\n"
            + "            GROUP BY division_id\n"
            + "        ) m ON m.divisionId = d.id\n"
            + "        WHERE d.is_active = 1\n"
            + "        ORDER BY d.name ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, monthKey);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ReportRow>>() {
      @Override
      @NonNull
      public List<ReportRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDivisionId = 0;
          final int _cursorIndexOfDivisionCode = 1;
          final int _cursorIndexOfDivisionName = 2;
          final int _cursorIndexOfJumlah = 3;
          final int _cursorIndexOfHadir = 4;
          final int _cursorIndexOfKurang = 5;
          final int _cursorIndexOfDinas = 6;
          final int _cursorIndexOfTerlambat = 7;
          final int _cursorIndexOfSakit = 8;
          final int _cursorIndexOfCuti = 9;
          final int _cursorIndexOfIzin = 10;
          final int _cursorIndexOfOffDinas = 11;
          final int _cursorIndexOfLainLain = 12;
          final List<ReportRow> _result = new ArrayList<ReportRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportRow _item;
            final long _tmpDivisionId;
            _tmpDivisionId = _cursor.getLong(_cursorIndexOfDivisionId);
            final String _tmpDivisionCode;
            _tmpDivisionCode = _cursor.getString(_cursorIndexOfDivisionCode);
            final String _tmpDivisionName;
            _tmpDivisionName = _cursor.getString(_cursorIndexOfDivisionName);
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
            _item = new ReportRow(_tmpDivisionId,_tmpDivisionCode,_tmpDivisionName,_tmpJumlah,_tmpHadir,_tmpKurang,_tmpDinas,_tmpTerlambat,_tmpSakit,_tmpCuti,_tmpIzin,_tmpOffDinas,_tmpLainLain);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object listWeekHistory(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT week_key FROM attendance_daily ORDER BY week_key DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object listMonthHistory(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT month_key FROM attendance_daily ORDER BY month_key DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
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
