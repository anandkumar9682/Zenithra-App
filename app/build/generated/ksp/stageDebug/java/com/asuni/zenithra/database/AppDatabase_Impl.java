package com.asuni.zenithra.database;

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
import com.asuni.zenithra.database.dao.MangaDao;
import com.asuni.zenithra.database.dao.MangaDao_Impl;
import com.asuni.zenithra.database.dao.UsersDao;
import com.asuni.zenithra.database.dao.UsersDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UsersDao _usersDao;

  private volatile MangaDao _mangaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `manga_items` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `subTitle` TEXT, `status` TEXT NOT NULL, `thumb` TEXT NOT NULL, `summary` TEXT NOT NULL, `authors` TEXT NOT NULL, `genres` TEXT NOT NULL, `nsfw` INTEGER NOT NULL, `type` TEXT NOT NULL, `totalChapter` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_details` (`userId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uid` TEXT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `photoUrl` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `emailVerified` TEXT NOT NULL, `accountCreated` TEXT NOT NULL, `lastSignIn` TEXT NOT NULL, `isUpdate` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7668176507984fdfb392e48f648a7d2d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `manga_items`");
        db.execSQL("DROP TABLE IF EXISTS `user_details`");
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
        final HashMap<String, TableInfo.Column> _columnsMangaItems = new HashMap<String, TableInfo.Column>(13);
        _columnsMangaItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("subTitle", new TableInfo.Column("subTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("thumb", new TableInfo.Column("thumb", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("summary", new TableInfo.Column("summary", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("authors", new TableInfo.Column("authors", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("genres", new TableInfo.Column("genres", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("nsfw", new TableInfo.Column("nsfw", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("totalChapter", new TableInfo.Column("totalChapter", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMangaItems.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMangaItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMangaItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMangaItems = new TableInfo("manga_items", _columnsMangaItems, _foreignKeysMangaItems, _indicesMangaItems);
        final TableInfo _existingMangaItems = TableInfo.read(db, "manga_items");
        if (!_infoMangaItems.equals(_existingMangaItems)) {
          return new RoomOpenHelper.ValidationResult(false, "manga_items(com.asuni.zenithra.domain.MangaItemEntity).\n"
                  + " Expected:\n" + _infoMangaItems + "\n"
                  + " Found:\n" + _existingMangaItems);
        }
        final HashMap<String, TableInfo.Column> _columnsUserDetails = new HashMap<String, TableInfo.Column>(10);
        _columnsUserDetails.put("userId", new TableInfo.Column("userId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("uid", new TableInfo.Column("uid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("photoUrl", new TableInfo.Column("photoUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("emailVerified", new TableInfo.Column("emailVerified", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("accountCreated", new TableInfo.Column("accountCreated", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("lastSignIn", new TableInfo.Column("lastSignIn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserDetails.put("isUpdate", new TableInfo.Column("isUpdate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserDetails = new TableInfo("user_details", _columnsUserDetails, _foreignKeysUserDetails, _indicesUserDetails);
        final TableInfo _existingUserDetails = TableInfo.read(db, "user_details");
        if (!_infoUserDetails.equals(_existingUserDetails)) {
          return new RoomOpenHelper.ValidationResult(false, "user_details(com.asuni.zenithra.domain.UserDetailsEntity).\n"
                  + " Expected:\n" + _infoUserDetails + "\n"
                  + " Found:\n" + _existingUserDetails);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7668176507984fdfb392e48f648a7d2d", "9ca34c38a39fd826e60a000d7487a13e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "manga_items","user_details");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `manga_items`");
      _db.execSQL("DELETE FROM `user_details`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
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
    _typeConvertersMap.put(UsersDao.class, UsersDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MangaDao.class, MangaDao_Impl.getRequiredConverters());
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
  public UsersDao getUsersDao() {
    if (_usersDao != null) {
      return _usersDao;
    } else {
      synchronized(this) {
        if(_usersDao == null) {
          _usersDao = new UsersDao_Impl(this);
        }
        return _usersDao;
      }
    }
  }

  @Override
  public MangaDao getMangaDao() {
    if (_mangaDao != null) {
      return _mangaDao;
    } else {
      synchronized(this) {
        if(_mangaDao == null) {
          _mangaDao = new MangaDao_Impl(this);
        }
        return _mangaDao;
      }
    }
  }
}
