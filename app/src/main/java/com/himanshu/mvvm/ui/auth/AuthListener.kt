package com.himanshu.mvvm.ui.auth

import androidx.lifecycle.LiveData
import com.himanshu.mvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)
}