package com.example.userdata.database

import android.provider.BaseColumns

object DatabaseInfo {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "userinfo"

        const val COLUMN_NAME = "name"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }
}