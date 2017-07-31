package com.clsan.byciclover.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.clsan.byciclover.models.User;

/**
 * Created by clsan on 06/04/2017.
 */
public class UserDataController {
  private static final String TABLE_USERS = "users";

  private static DatabaseHelper mDatabaseHelper;

  /* package private access controlelr. */
  private static class Columns {
    static final String ID = "_id";
    static final String NICKNAME = "nickname";
  }

  public UserDataController(Context context) {
    mDatabaseHelper = DatabaseHelper.getInstance(context);
  }

  public int getCount() {
    SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

    Cursor cursor = db.rawQuery("SELECT * FROM users", null);
    try {
      return cursor.getCount();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
  }

  public User getUser() {
    SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
    Integer id = null;
    String nickname = null;

    User user = new User();

    Cursor cursor = db.rawQuery("SELECT * FROM users WHERE _id = '1'", null);
    try {
      if (cursor.moveToFirst()) {
        id = cursor.getInt(cursor.getColumnIndex(Columns.ID));
        nickname = cursor.getString(cursor.getColumnIndex(Columns.NICKNAME));
      } else {
        Log.e("getUser", "must never reach here.");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }

    user.setMId(id);
    user.setMNickname(nickname);

    return user;
  }

  public User upsertUser(User user, Actions action) {
    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
    long id = -1;

    db.beginTransaction();

    try{
      ContentValues values = new ContentValues();
      values.put(Columns.NICKNAME, user.getMNickname());

      if (Actions.INSERT.equals(action)) {
        id = db.insertOrThrow(TABLE_USERS,null, values);
      } else if (Actions.UPDATE.equals(action)) {
        String whereClause = Columns.ID + "= ?";
        String[] whereArgs = {String.valueOf(1)};
        db.update(TABLE_USERS, values, whereClause, whereArgs);
      }

      db.setTransactionSuccessful();
    } catch (Exception e) {
      Log.d("upsertUser", "Error while trying to add or update user");
    } finally {
      db.endTransaction();
    }

    return user;
  }
}
