package com.asuni.zenithra.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.asuni.zenithra.domain.MangaItemEntity;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MangaDao_Impl implements MangaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MangaItemEntity> __insertionAdapterOfMangaItemEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public MangaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMangaItemEntity = new EntityInsertionAdapter<MangaItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `manga_items` (`id`,`title`,`subTitle`,`status`,`thumb`,`summary`,`authors`,`genres`,`nsfw`,`type`,`totalChapter`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MangaItemEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        if (entity.getSubTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubTitle());
        }
        statement.bindString(4, entity.getStatus());
        statement.bindString(5, entity.getThumb());
        statement.bindString(6, entity.getSummary());
        statement.bindString(7, entity.getAuthors());
        statement.bindString(8, entity.getGenres());
        final int _tmp = entity.getNsfw() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindString(10, entity.getType());
        statement.bindLong(11, entity.getTotalChapter());
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM manga_items";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<MangaItemEntity> mangaList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMangaItemEntity.insert(mangaList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllManga(final Continuation<? super List<MangaItemEntity>> $completion) {
    final String _sql = "SELECT * FROM manga_items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MangaItemEntity>>() {
      @Override
      @NonNull
      public List<MangaItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subTitle");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfThumb = CursorUtil.getColumnIndexOrThrow(_cursor, "thumb");
          final int _cursorIndexOfSummary = CursorUtil.getColumnIndexOrThrow(_cursor, "summary");
          final int _cursorIndexOfAuthors = CursorUtil.getColumnIndexOrThrow(_cursor, "authors");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfNsfw = CursorUtil.getColumnIndexOrThrow(_cursor, "nsfw");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTotalChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "totalChapter");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<MangaItemEntity> _result = new ArrayList<MangaItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MangaItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpSubTitle;
            if (_cursor.isNull(_cursorIndexOfSubTitle)) {
              _tmpSubTitle = null;
            } else {
              _tmpSubTitle = _cursor.getString(_cursorIndexOfSubTitle);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpThumb;
            _tmpThumb = _cursor.getString(_cursorIndexOfThumb);
            final String _tmpSummary;
            _tmpSummary = _cursor.getString(_cursorIndexOfSummary);
            final String _tmpAuthors;
            _tmpAuthors = _cursor.getString(_cursorIndexOfAuthors);
            final String _tmpGenres;
            _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            final boolean _tmpNsfw;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNsfw);
            _tmpNsfw = _tmp != 0;
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final int _tmpTotalChapter;
            _tmpTotalChapter = _cursor.getInt(_cursorIndexOfTotalChapter);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MangaItemEntity(_tmpId,_tmpTitle,_tmpSubTitle,_tmpStatus,_tmpThumb,_tmpSummary,_tmpAuthors,_tmpGenres,_tmpNsfw,_tmpType,_tmpTotalChapter,_tmpCreatedAt,_tmpUpdatedAt);
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
