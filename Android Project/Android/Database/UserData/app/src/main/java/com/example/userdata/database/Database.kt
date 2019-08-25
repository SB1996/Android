package com.example.userdata.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class Database(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TAG: String = Database::class.java.simpleName
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "userdata"
    }
    inner class SQLiquery{
         val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseInfo.FeedEntry.TABLE_NAME} (" +
                         "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                         "${DatabaseInfo.FeedEntry.COLUMN_NAME} TEXT," +
                         "${DatabaseInfo.FeedEntry.COLUMN_USERNAME} TEXT," +
                         "${DatabaseInfo.FeedEntry.COLUMN_EMAIL} TEXT," +
                         "${DatabaseInfo.FeedEntry.COLUMN_PASSWORD} TEXT" +
            ")"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseInfo.FeedEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreate: onCreated() called")
        db!!.execSQL(SQLitequery.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: onUpgrade() called")
        db.execSQL(SQLitequery.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onDowngrade: onDowngrade() called")
        onUpgrade(db, oldVersion, newVersion)
    }
}