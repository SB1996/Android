package com.example.mvvmarchitecture.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmarchitecture.data.db.dao.SingupUserDao
import com.example.mvvmarchitecture.data.db.entity.SingupUser

@Database(entities = [
    SingupUser::class
], version = MainDatabase.DATABASE_VERSION)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getSingupUserDao() : SingupUserDao


    companion object {
        val DATABASE_NAME: String = "main_database.db"
        const val DATABASE_VERSION: Int = 1

        @Volatile
        private var DB_INSTANCE: MainDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = DB_INSTANCE ?: synchronized(LOCK){
            DB_INSTANCE ?: buildDatabase(context).also {
                DB_INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context): MainDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}