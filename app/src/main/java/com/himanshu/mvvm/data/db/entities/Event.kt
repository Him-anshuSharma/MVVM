package com.himanshu.mvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Event(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val title: String,
    val description: String,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val createdOn: String,
    val __v: Int
): Serializable