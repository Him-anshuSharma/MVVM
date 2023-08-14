package com.himanshu.mvvm.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import com.himanshu.mvvm.R

class NotificationHelper(base: Context) : ContextWrapper(base) {
    private val channelId = "21BCI0253"
    val channelName = "HimanshuMVVM"

    var notificationManager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    private fun createChannel(){
        val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
        getManager()?.createNotificationChannel(channel)
    }

    fun getManager():NotificationManager?{
        if(notificationManager == null){
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    fun getChannelNotification(name: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(applicationContext,channelId)
            .setContentTitle("Event Reminder")
            .setContentText(name)
            .setSmallIcon(R.drawable.ic_note)
    }


}