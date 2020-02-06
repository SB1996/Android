package com.example.userdata

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.userdata.database.Database
import com.example.userdata.database.DatabaseInfo
import com.example.userdata.database.SQLitequery.Companion.SQL_CREATE_ENTRIES

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var nameTV: EditText
    private lateinit var usernameTV: EditText
    private lateinit var emailTV: EditText
    private lateinit var passwordTV: EditText
    private lateinit var fatahTV: EditText
    private lateinit var statusTV: TextView
    private lateinit var clearTV: TextView

    private lateinit var insertBTN: Button
    private lateinit var updateBTN: Button
    private lateinit var viewBTN: Button
    private lateinit var editBTN: Button
    private lateinit var deleteBTN: Button

    var Name: String? = null
    var UserName: String? = null
    var Email: String? = null
    var Password: String? = null

    var dbHelper: Database? = null

    private fun initialization(){
        Log.d(TAG, "initialization: initialization() called")

        nameTV = findViewById(R.id.et_name) as EditText
        usernameTV = findViewById(R.id.et_username) as EditText
        emailTV = findViewById(R.id.et_email) as EditText
        passwordTV = findViewById(R.id.et_password) as EditText
        fatahTV = findViewById(R.id.et_fatch_rc) as EditText
        statusTV = findViewById(R.id.tv_status) as TextView
        clearTV = findViewById(R.id.tv_clear) as TextView

        insertBTN = findViewById(R.id.btn_insert) as Button
        updateBTN = findViewById(R.id.btn_update) as Button
        viewBTN = findViewById(R.id.btn_view_rc) as Button
        editBTN = findViewById(R.id.btn_edit) as Button
        deleteBTN = findViewById(R.id.btn_delete) as Button
    }

    private fun getData(){
        Log.d(TAG, "getData: getData() called")

        Name = nameTV.text.toString()
        UserName = usernameTV.text.toString()
        Email = emailTV.text.toString()
        Password = passwordTV.text.toString()
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()

        dbHelper = Database(this@MainActivity)
        Log.d(TAG, "onCreate: ${SQL_CREATE_ENTRIES}")

        //Click event for INSERT Button
        insertBTN.setOnClickListener {
            Log.d(TAG, "onCreate: INSERT Button Clicked")
            getData()
            if(Name!!.isNotEmpty() && UserName!!.isNotEmpty() && Email!!.isNotEmpty() && Password!!.isNotEmpty()){

                // Gets the data repository in write mode
                val db = dbHelper!!.writableDatabase

                // Create a new map of values, where column names are the keys
                val data = ContentValues().apply {
                    put(DatabaseInfo.FeedEntry.COLUMN_NAME, Name)
                    put(DatabaseInfo.FeedEntry.COLUMN_USERNAME, UserName)
                    put(DatabaseInfo.FeedEntry.COLUMN_EMAIL, Email)
                    put(DatabaseInfo.FeedEntry.COLUMN_PASSWORD, Password)
                }

                // Insert the new row, returning the primary key value of the new row
                val newRowId = db?.insert(DatabaseInfo.FeedEntry.TABLE_NAME, null, data)
            }else{
                Log.d(TAG, "onCreate: you should enter a value for all field")
            }
        }

        //Click event for VIEW RECORD Button
        viewBTN.setOnClickListener {

            Log.d(TAG, "onCreate: VIEW RECORD Button Clicked")
            val db = dbHelper!!.readableDatabase

            val needColumns = arrayOf(
                DatabaseInfo.FeedEntry.COLUMN_NAME,
                DatabaseInfo.FeedEntry.COLUMN_USERNAME,
                DatabaseInfo.FeedEntry.COLUMN_EMAIL,
                DatabaseInfo.FeedEntry.COLUMN_PASSWORD
            )
            val selection = "${DatabaseInfo.FeedEntry.COLUMN_NAME} = ?"
            val selectionArgs = arrayOf("Santanu")
            val sortOrder = "${DatabaseInfo.FeedEntry.COLUMN_NAME} DESC"

            val cursor = db.query(
                DatabaseInfo.FeedEntry.TABLE_NAME,   // The table to query
                needColumns,                         // The array of columns to return (pass null to get all)
                selection,                           // The columns for the WHERE clause
                selectionArgs,                       // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                sortOrder                            // The sort order
            )

            val cursorObj = db.query(
                DatabaseInfo.FeedEntry.TABLE_NAME,   // The table to query
                null,                         // The array of columns to return (pass null to get all)
                null,                           // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                null                            // The sort order
            )

            if(cursorObj != null){
                with(cursorObj) {
                    while (moveToNext()) {
                        val name = getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_NAME)).toString()
                        val username = getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_USERNAME)).toString()
                        val email = getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_EMAIL)).toString()
                        val password = getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_PASSWORD)).toString()

                        statusTV.append("\nName : ${getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_NAME)).toString()}")
                        statusTV.append("\nUserName : ${getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_USERNAME)).toString()}")
                        statusTV.append("\nEmail : ${getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_EMAIL)).toString()}")
                        statusTV.append("\nPassword : ${getString(getColumnIndexOrThrow(DatabaseInfo.FeedEntry.COLUMN_PASSWORD)).toString()}")
                    }
                }

            }


        }

        //Click event for VIEW RECORD Button
        clearTV.setOnClickListener {
            statusTV.text = ""
            statusTV.clearComposingText()
        }
    }
    override fun onDestroy() {
        dbHelper!!.close()
        super.onDestroy()
    }
}
