package com.example.stack.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Stack.db"
        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StackEntry.TABLE_NAME + " (" + StackEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    StackEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    StackEntry.COLUMN_NAME_PASSWORD + " TEXT )"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + StackEntry.TABLE_NAME
    }
    // Inner class ...
    class StackEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "singup"

            val COLUMN_NAME_ID = "_id"
            val COLUMN_NAME_USERNAME = "username"
            val COLUMN_NAME_PASSWORD = "password"
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }


}

