package com.himanshu.mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.himanshu.mvvm.data.db.entities.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(alarm: Alarm): Long

    @Query( "SELECT * FROM Alarm where EventId = :eventId")
    fun getAlarmChannelId(eventId:String): LiveData<Alarm>
}