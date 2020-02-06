package com.example.mvvmarchitecture.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmarchitecture.data.db.entity.SingupUser

@Dao
interface SingupUserDao {

    /** get singupUser **/
    @Query("SELECT * FROM SingupUser WHERE username = :username ")
    suspend fun getSingupUser(username: String) : SingupUser

    /** insert singupUser **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingupUser(vararg singupUser: SingupUser)

    /** delete singupUser **/
    @Delete
    suspend fun deleteSingupUser(singupUser: SingupUser)

    /** update singupUser **/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSingupUser(singupUser: SingupUser)

    /** update singupUser UserName **/
    @Query("UPDATE SingupUser SET email= :newUsername WHERE username= :username")
    suspend fun updateSingupUserUsername(username: String, newUsername: String)

    /** update singupUser Password **/
    @Query("UPDATE SingupUser SET password= :password WHERE username= :username")
    suspend fun updateSingupUserPassword(username: String, password: String)

    /** update singupUser Email **/
    @Query("UPDATE SingupUser SET email= :email WHERE username= :username")
    suspend fun updateSingupUserEmail(username: String, email: String)
}