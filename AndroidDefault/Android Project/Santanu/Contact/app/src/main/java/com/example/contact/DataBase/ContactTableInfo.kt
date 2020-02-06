package com.example.contact.DataBase

class ContactTableInfo {
    companion object{

        val TABLE_CONTACT = "contactTable"

        val COLUMN_ID = "contactId"
        val COLUMN_NAME = "contactName"
        val COLUMN_EMAIL = "contactEmil"
        val COLUMN_NUMBER = "contactNumber"
        val COLUMN_PROFILE_IMG = "contactProfile"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_NUMBER, COLUMN_PROFILE_IMG
        )

        val CREATE_TABLE = "CREATE TABLE $TABLE_CONTACT($COLUMN_ID TEXT PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT, $COLUMN_NUMBER NUMBER, $COLUMN_PROFILE_IMG TEXT)"
        val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_CONTACT"


    }
}