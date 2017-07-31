package com.clsan.byciclover.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by flecho on 2017. 2. 3..
 * referring to https://guides.codepath.com/android/Local-Databases-with-SQLiteOpenHelper
 */

public class DatabaseHelper extends SQLiteOpenHelper {
  private static DatabaseHelper sInstance;

  private static final String DATABASE_NAME = "bycicloverDatabase";
  private static final int DATABASE_VERSION = 2;

  // Table name
  private static final String TABLE_USERS = "users";

  // Goal Table Columns
  private static final String COL_USER_ID = "_id"; // This is extremely important.
  private static final String COL_USER_NICKNAME = "nickname";


  public static synchronized DatabaseHelper getInstance(Context context) {
        /* Use the application context, which will ensure that you don't accidentally leak an
        * Activity's context. See this article for more information: http://bit.ly/6LRzfx*/
    if (sInstance == null) {
      sInstance = new DatabaseHelper(context.getApplicationContext());
    }
    return sInstance;
  }

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Called when the database connection is being configured.
  // Configure database settings for things like foreign key support, write-ahead logging.
  @Override
  public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    //db.setForeignKeyConstraintsEnabled(true);
  }

  // Called when the database is created for the FIRST time.
  // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
  @Override
  public void onCreate(SQLiteDatabase db){
    String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
        "(" +
        COL_USER_ID + " INTEGER PRIMARY KEY," + // Define a primary key
        COL_USER_NICKNAME + " TEXT NOT NULL" +
        ")";

    db.execSQL(CREATE_USERS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    if (oldVersion != newVersion) {
      // Simplest implementation is to drop all old tables and recreate them.
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
      onCreate(db);
    }
  }

  /* This method must only be used by reflection(). Otherwise, you must not use it.*/
  public Cursor getOrderedCursor() {
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery("SELECT * FROM users ORDER BY list_index ASC", null); // ORDER BY list_index ASC
  }

  public Cursor getNewCursor() {
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery("SELECT * FROM users ORDER BY list_index ASC", null);
  }
}
