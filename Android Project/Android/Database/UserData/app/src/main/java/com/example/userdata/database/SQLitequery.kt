package com.example.userdata.database

import android.provider.BaseColumns

class SQLitequeary {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${DatabaseInfo.FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${DatabaseInfo.FeedEntry.COLUMN_NAME} TEXT," +
                "${DatabaseInfo.FeedEntry.COLUMN_USERNAME} TEXT" +
                "${DatabaseInfo.FeedEntry.COLUMN_EMAIL} TEXT" +
                "${DatabaseInfo.FeedEntry.COLUMN_PASSWORD} TEXT" +
                ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseInfo.FeedEntry.TABLE_NAME}"
}