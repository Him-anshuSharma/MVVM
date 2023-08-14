package com.himanshu.mvvm.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlertReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val eventName = intent?.getStringExtra("EventName")
        val notificationHelper = NotificationHelper(context)
        val nb = notificationHelper.getChannelNotification(eventName.toString())
        notificationHelper.getManager()?.notify(1,nb.build())
    }
}