package com.example.stack.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;

public class DatabaseJava extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Stack.db";
    // Inner class ...
    public static class StackEntry implements BaseColumns {
        public static final String TABLE_NAME = "singup";

        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StackEntry.TABLE_NAME + " (" + StackEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                                                           StackEntry.COLUMN_NAME_USERNAME + " TEXT," +
                                                           StackEntry.COLUMN_NAME_PASSWORD + " TEXT )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + StackEntry.TABLE_NAME;

    // Constructor ...
    public DatabaseJava(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
