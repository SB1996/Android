package com.example.contact.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ContactDatabaseInit(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private val TAG = "CONTACT_DATABASE"
        private val DATABASE_NAME = "contact.db"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate: Database created")
        db.execSQL(ContactTableInfo.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: Database updated...")
        db.execSQL(ContactTableInfo.DELETE_TABLE)
        onCreate(db)
    }
}