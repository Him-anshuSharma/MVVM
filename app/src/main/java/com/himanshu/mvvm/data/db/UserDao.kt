package com.himanshu.mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.himanshu.mvvm.data.db.entities.CURR_USER_ID
import com.himanshu.mvvm.data.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user:User): Long

    @Query( "SELECT * FROM user WHERE uid  = $CURR_USER_ID")
    fun getUser(): LiveData<User>
}