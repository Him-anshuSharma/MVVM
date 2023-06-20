package com.himanshu.mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.himanshu.mvvm.data.db.entities.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEvents(events:List<Event>)

    @Query("SELECT * FROM Event")
    fun getEvents():LiveData<List<Event>>

}