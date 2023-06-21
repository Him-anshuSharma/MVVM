package com.himanshu.mvvm.ui.home.events


interface EventsListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message:String)
}