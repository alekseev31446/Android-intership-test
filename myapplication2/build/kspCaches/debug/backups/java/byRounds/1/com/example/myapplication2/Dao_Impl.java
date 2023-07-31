package com.example.myapplication2;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Dao_Impl implements Dao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Animal> __insertionAdapterOfAnimal;

  private final EntityDeletionOrUpdateAdapter<Animal> __updateAdapterOfAnimal;

  public Dao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnimal = new EntityInsertionAdapter<Animal>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `animals` (`id`,`imageFilePath`,`isLiked`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Animal value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getImageFilePath() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageFilePath());
        }
        final int _tmp = value.isLiked() ? 1 : 0;
        stmt.bindLong(3, _tmp);
      }
    };
    this.__updateAdapterOfAnimal = new EntityDeletionOrUpdateAdapter<Animal>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `animals` SET `id` = ?,`imageFilePath` = ?,`isLiked` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Animal value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getImageFilePath() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageFilePath());
        }
        final int _tmp = value.isLiked() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getId());
        }
      }
    };
  }

  @Override
  public Object insertAnimal(final Animal animal, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAnimal.insert(animal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAnimals(final List<Animal> animals,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAnimal.insert(animals);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public void updateAnimal(final Animal animal) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAnimal.handle(animal);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Animal>> getAllAnimals() {
    final String _sql = "SELECT * FROM animals";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"animals"}, false, new Callable<List<Animal>>() {
      @Override
      public List<Animal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImageFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imageFilePath");
          final int _cursorIndexOfIsLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLiked");
          final List<Animal> _result = new ArrayList<Animal>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Animal _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpImageFilePath;
            if (_cursor.isNull(_cursorIndexOfImageFilePath)) {
              _tmpImageFilePath = null;
            } else {
              _tmpImageFilePath = _cursor.getString(_cursorIndexOfImageFilePath);
            }
            final boolean _tmpIsLiked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLiked);
            _tmpIsLiked = _tmp != 0;
            _item = new Animal(_tmpId,_tmpImageFilePath,_tmpIsLiked);
            _result.add(_item);
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

  @Override
  public LiveData<List<Animal>> getLikedAnimals() {
    final String _sql = "SELECT * FROM animals WHERE isLiked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"animals"}, false, new Callable<List<Animal>>() {
      @Override
      public List<Animal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImageFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imageFilePath");
          final int _cursorIndexOfIsLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLiked");
          final List<Animal> _result = new ArrayList<Animal>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Animal _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpImageFilePath;
            if (_cursor.isNull(_cursorIndexOfImageFilePath)) {
              _tmpImageFilePath = null;
            } else {
              _tmpImageFilePath = _cursor.getString(_cursorIndexOfImageFilePath);
            }
            final boolean _tmpIsLiked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLiked);
            _tmpIsLiked = _tmp != 0;
            _item = new Animal(_tmpId,_tmpImageFilePath,_tmpIsLiked);
            _result.add(_item);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
