package com.himanshu.mvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    val eventName: String,
    val EventId: String,
    @PrimaryKey(autoGenerate = false)
    val AlarmChannelId: Int,
    val Time: Long,
)