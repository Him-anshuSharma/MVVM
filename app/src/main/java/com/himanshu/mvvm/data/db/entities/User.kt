package com.himanshu.mvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURR_USER_ID = 0

@Entity
data class User(
    var id:Int? = null,
    var username: String? = null,
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURR_USER_ID
}