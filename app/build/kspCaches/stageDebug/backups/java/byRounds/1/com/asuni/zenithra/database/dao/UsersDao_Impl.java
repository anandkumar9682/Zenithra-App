package com.asuni.zenithra.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.asuni.zenithra.domain.UserDetailsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UsersDao_Impl implements UsersDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserDetailsEntity> __insertionAdapterOfUserDetailsEntity;

  private final EntityDeletionOrUpdateAdapter<UserDetailsEntity> __deletionAdapterOfUserDetailsEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllUsers;

  public UsersDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserDetailsEntity = new EntityInsertionAdapter<UserDetailsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_details` (`userId`,`uid`,`name`,`email`,`photoUrl`,`phoneNumber`,`emailVerified`,`accountCreated`,`lastSignIn`,`isUpdate`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserDetailsEntity entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindString(2, entity.getUid());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getEmail());
        statement.bindString(5, entity.getPhotoUrl());
        statement.bindString(6, entity.getPhoneNumber());
        statement.bindString(7, entity.getEmailVerified());
        statement.bindString(8, entity.getAccountCreated());
        statement.bindString(9, entity.getLastSignIn());
        final int _tmp = entity.isUpdate() ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
    this.__deletionAdapterOfUserDetailsEntity = new EntityDeletionOrUpdateAdapter<UserDetailsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_details` WHERE `userId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserDetailsEntity entity) {
        statement.bindLong(1, entity.getUserId());
      }
    };
    this.__preparedStmtOfDeleteAllUsers = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_details";
        return _query;
      }
    };
  }

  @Override
  public void insertUser(final UserDetailsEntity singInResponseResult) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserDetailsEntity.insert(singInResponseResult);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSingInResponseResult(final UserDetailsEntity SingInResponseResult) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserDetailsEntity.handle(SingInResponseResult);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllUsers() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllUsers.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllUsers.release(_stmt);
    }
  }

  @Override
  public UserDetailsEntity findSingInResponseResult(final int id) {
    final String _sql = "SELECT * FROM user_details WHERE userId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfEmailVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "emailVerified");
      final int _cursorIndexOfAccountCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "accountCreated");
      final int _cursorIndexOfLastSignIn = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSignIn");
      final int _cursorIndexOfIsUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "isUpdate");
      final UserDetailsEntity _result;
      if (_cursor.moveToFirst()) {
        final long _tmpUserId;
        _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
        final String _tmpUid;
        _tmpUid = _cursor.getString(_cursorIndexOfUid);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhotoUrl;
        _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
        final String _tmpPhoneNumber;
        _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        final String _tmpEmailVerified;
        _tmpEmailVerified = _cursor.getString(_cursorIndexOfEmailVerified);
        final String _tmpAccountCreated;
        _tmpAccountCreated = _cursor.getString(_cursorIndexOfAccountCreated);
        final String _tmpLastSignIn;
        _tmpLastSignIn = _cursor.getString(_cursorIndexOfLastSignIn);
        final boolean _tmpIsUpdate;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsUpdate);
        _tmpIsUpdate = _tmp != 0;
        _result = new UserDetailsEntity(_tmpUserId,_tmpUid,_tmpName,_tmpEmail,_tmpPhotoUrl,_tmpPhoneNumber,_tmpEmailVerified,_tmpAccountCreated,_tmpLastSignIn,_tmpIsUpdate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public UserDetailsEntity getUser() {
    final String _sql = "SELECT * FROM user_details LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
      final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
      final int _cursorIndexOfEmailVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "emailVerified");
      final int _cursorIndexOfAccountCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "accountCreated");
      final int _cursorIndexOfLastSignIn = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSignIn");
      final int _cursorIndexOfIsUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "isUpdate");
      final UserDetailsEntity _result;
      if (_cursor.moveToFirst()) {
        final long _tmpUserId;
        _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
        final String _tmpUid;
        _tmpUid = _cursor.getString(_cursorIndexOfUid);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhotoUrl;
        _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
        final String _tmpPhoneNumber;
        _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
        final String _tmpEmailVerified;
        _tmpEmailVerified = _cursor.getString(_cursorIndexOfEmailVerified);
        final String _tmpAccountCreated;
        _tmpAccountCreated = _cursor.getString(_cursorIndexOfAccountCreated);
        final String _tmpLastSignIn;
        _tmpLastSignIn = _cursor.getString(_cursorIndexOfLastSignIn);
        final boolean _tmpIsUpdate;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsUpdate);
        _tmpIsUpdate = _tmp != 0;
        _result = new UserDetailsEntity(_tmpUserId,_tmpUid,_tmpName,_tmpEmail,_tmpPhotoUrl,_tmpPhoneNumber,_tmpEmailVerified,_tmpAccountCreated,_tmpLastSignIn,_tmpIsUpdate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<UserDetailsEntity> getUserLive() {
    final String _sql = "SELECT * FROM user_details LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_details"}, false, new Callable<UserDetailsEntity>() {
      @Override
      @Nullable
      public UserDetailsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmailVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "emailVerified");
          final int _cursorIndexOfAccountCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "accountCreated");
          final int _cursorIndexOfLastSignIn = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSignIn");
          final int _cursorIndexOfIsUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "isUpdate");
          final UserDetailsEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpPhotoUrl;
            _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmailVerified;
            _tmpEmailVerified = _cursor.getString(_cursorIndexOfEmailVerified);
            final String _tmpAccountCreated;
            _tmpAccountCreated = _cursor.getString(_cursorIndexOfAccountCreated);
            final String _tmpLastSignIn;
            _tmpLastSignIn = _cursor.getString(_cursorIndexOfLastSignIn);
            final boolean _tmpIsUpdate;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUpdate);
            _tmpIsUpdate = _tmp != 0;
            _result = new UserDetailsEntity(_tmpUserId,_tmpUid,_tmpName,_tmpEmail,_tmpPhotoUrl,_tmpPhoneNumber,_tmpEmailVerified,_tmpAccountCreated,_tmpLastSignIn,_tmpIsUpdate);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
