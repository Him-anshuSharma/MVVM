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

    @Query("SELECT * FROM Event WHERE startDateTime LIKE :givenDateInString")
    fun getEventByDate(givenDateInString:String):LiveData<List<Event>>

    @Query("DELETE FROM Event")
    fun deleteEvents():Int

    @Query("SELECT * FROM Event")
    fun getEvents():LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvent(event:Event)

}