package com.example.absenbnn.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.absenbnn.data.local.dao.AttendanceDao;
import com.example.absenbnn.data.local.dao.AttendanceDao_Impl;
import com.example.absenbnn.data.local.dao.DivisionDao;
import com.example.absenbnn.data.local.dao.DivisionDao_Impl;
import com.example.absenbnn.data.local.dao.ReportDao;
import com.example.absenbnn.data.local.dao.ReportDao_Impl;
import com.example.absenbnn.data.local.dao.UserDao;
import com.example.absenbnn.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile DivisionDao _divisionDao;

  private volatile AttendanceDao _attendanceDao;

  private volatile ReportDao _reportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `password_hash` TEXT NOT NULL, `full_name` TEXT NOT NULL, `role` TEXT NOT NULL, `is_active` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_username` ON `users` (`username`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `divisions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `code` TEXT NOT NULL, `name` TEXT NOT NULL, `is_active` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_divisions_code` ON `divisions` (`code`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attendance_daily` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `division_id` INTEGER NOT NULL, `attendance_date` TEXT NOT NULL, `year` INTEGER NOT NULL, `month` INTEGER NOT NULL, `month_key` TEXT NOT NULL, `week_key` TEXT NOT NULL, `jumlah` INTEGER NOT NULL, `hadir` INTEGER NOT NULL, `kurang` INTEGER NOT NULL, `dinas` INTEGER NOT NULL, `terlambat` INTEGER NOT NULL, `sakit` INTEGER NOT NULL, `cuti` INTEGER NOT NULL, `izin` INTEGER NOT NULL, `off_dinas` INTEGER NOT NULL, `lain_lain` INTEGER NOT NULL, `lain_lain_note` TEXT, `created_by` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`division_id`) REFERENCES `divisions`(`id`) ON UPDATE CASCADE ON DELETE RESTRICT , FOREIGN KEY(`created_by`) REFERENCES `users`(`id`) ON UPDATE CASCADE ON DELETE RESTRICT )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attendance_daily_division_id_attendance_date` ON `attendance_daily` (`division_id`, `attendance_date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_attendance_daily_week_key` ON `attendance_daily` (`week_key`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_attendance_daily_month_key` ON `attendance_daily` (`month_key`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0240eccd54668fdf0f7ce3095b42e0e3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `divisions`");
        db.execSQL("DROP TABLE IF EXISTS `attendance_daily`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(8);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password_hash", new TableInfo.Column("password_hash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("full_name", new TableInfo.Column("full_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_username", true, Arrays.asList("username"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.absenbnn.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsDivisions = new HashMap<String, TableInfo.Column>(6);
        _columnsDivisions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDivisions.put("code", new TableInfo.Column("code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDivisions.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDivisions.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDivisions.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDivisions.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDivisions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDivisions = new HashSet<TableInfo.Index>(1);
        _indicesDivisions.add(new TableInfo.Index("index_divisions_code", true, Arrays.asList("code"), Arrays.asList("ASC")));
        final TableInfo _infoDivisions = new TableInfo("divisions", _columnsDivisions, _foreignKeysDivisions, _indicesDivisions);
        final TableInfo _existingDivisions = TableInfo.read(db, "divisions");
        if (!_infoDivisions.equals(_existingDivisions)) {
          return new RoomOpenHelper.ValidationResult(false, "divisions(com.example.absenbnn.data.local.entity.DivisionEntity).\n"
                  + " Expected:\n" + _infoDivisions + "\n"
                  + " Found:\n" + _existingDivisions);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendanceDaily = new HashMap<String, TableInfo.Column>(21);
        _columnsAttendanceDaily.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("division_id", new TableInfo.Column("division_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("attendance_date", new TableInfo.Column("attendance_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("year", new TableInfo.Column("year", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("month", new TableInfo.Column("month", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("month_key", new TableInfo.Column("month_key", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("week_key", new TableInfo.Column("week_key", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("hadir", new TableInfo.Column("hadir", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("kurang", new TableInfo.Column("kurang", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("dinas", new TableInfo.Column("dinas", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("terlambat", new TableInfo.Column("terlambat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("sakit", new TableInfo.Column("sakit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("cuti", new TableInfo.Column("cuti", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("izin", new TableInfo.Column("izin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("off_dinas", new TableInfo.Column("off_dinas", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("lain_lain", new TableInfo.Column("lain_lain", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("lain_lain_note", new TableInfo.Column("lain_lain_note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("created_by", new TableInfo.Column("created_by", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendanceDaily.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendanceDaily = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysAttendanceDaily.add(new TableInfo.ForeignKey("divisions", "RESTRICT", "CASCADE", Arrays.asList("division_id"), Arrays.asList("id")));
        _foreignKeysAttendanceDaily.add(new TableInfo.ForeignKey("users", "RESTRICT", "CASCADE", Arrays.asList("created_by"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAttendanceDaily = new HashSet<TableInfo.Index>(3);
        _indicesAttendanceDaily.add(new TableInfo.Index("index_attendance_daily_division_id_attendance_date", true, Arrays.asList("division_id", "attendance_date"), Arrays.asList("ASC", "ASC")));
        _indicesAttendanceDaily.add(new TableInfo.Index("index_attendance_daily_week_key", false, Arrays.asList("week_key"), Arrays.asList("ASC")));
        _indicesAttendanceDaily.add(new TableInfo.Index("index_attendance_daily_month_key", false, Arrays.asList("month_key"), Arrays.asList("ASC")));
        final TableInfo _infoAttendanceDaily = new TableInfo("attendance_daily", _columnsAttendanceDaily, _foreignKeysAttendanceDaily, _indicesAttendanceDaily);
        final TableInfo _existingAttendanceDaily = TableInfo.read(db, "attendance_daily");
        if (!_infoAttendanceDaily.equals(_existingAttendanceDaily)) {
          return new RoomOpenHelper.ValidationResult(false, "attendance_daily(com.example.absenbnn.data.local.entity.AttendanceDailyEntity).\n"
                  + " Expected:\n" + _infoAttendanceDaily + "\n"
                  + " Found:\n" + _existingAttendanceDaily);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0240eccd54668fdf0f7ce3095b42e0e3", "5f6dd6f2e22fade6ae641f6c5f0c106e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","divisions","attendance_daily");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `attendance_daily`");
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `divisions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DivisionDao.class, DivisionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AttendanceDao.class, AttendanceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReportDao.class, ReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public DivisionDao divisionDao() {
    if (_divisionDao != null) {
      return _divisionDao;
    } else {
      synchronized(this) {
        if(_divisionDao == null) {
          _divisionDao = new DivisionDao_Impl(this);
        }
        return _divisionDao;
      }
    }
  }

  @Override
  public AttendanceDao attendanceDao() {
    if (_attendanceDao != null) {
      return _attendanceDao;
    } else {
      synchronized(this) {
        if(_attendanceDao == null) {
          _attendanceDao = new AttendanceDao_Impl(this);
        }
        return _attendanceDao;
      }
    }
  }

  @Override
  public ReportDao reportDao() {
    if (_reportDao != null) {
      return _reportDao;
    } else {
      synchronized(this) {
        if(_reportDao == null) {
          _reportDao = new ReportDao_Impl(this);
        }
        return _reportDao;
      }
    }
  }
}
