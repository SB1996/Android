package com.example.contact.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.util.Log
import com.example.contact.Data.ContactUserData
import com.example.contact.Data.UserDataProvider


class ContactDatabaseManage(private var mContext: Context?) {

    private val TAG = "DATABASE_MANEGER"
    private var mContactDatabase: ContactDatabaseInit? = null
    private var sqLiteDatabase: SQLiteDatabase? = null
    init {
        mContactDatabase = ContactDatabaseInit(mContext!!)
        sqLiteDatabase = mContactDatabase?.writableDatabase
    }


    fun insertData(contactUserData: ContactUserData) {

        val values = ContentValues(5)

        values.put(ContactTableInfo.COLUMN_ID, contactUserData.contactId)
        values.put(ContactTableInfo.COLUMN_NAME, contactUserData.name)
        values.put(ContactTableInfo.COLUMN_EMAIL, contactUserData.email)
        values.put(ContactTableInfo.COLUMN_NUMBER, contactUserData.contactNumber)
        values.put(ContactTableInfo.COLUMN_PROFILE_IMG, contactUserData.profileImg)

        val insertStatus = sqLiteDatabase?.insert(ContactTableInfo.TABLE_CONTACT, null, values)
        if(insertStatus != null){
            Log.d(TAG, "Insert Data : Succeeded")
        }else{
            Log.d(TAG, "Insert Data : Failed")
        }
    }
    fun seedDatabase() {
        val userData = UserDataProvider.userDataList
        for (data in userData) {
            insertData(data)
        }
    }
    fun openConnection() { sqLiteDatabase = mContactDatabase?.getWritableDatabase() }
    fun closeConnection() { sqLiteDatabase?.close() }
}