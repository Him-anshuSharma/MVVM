package com.himanshu.mvvm.data.repository

import android.content.Context
import android.util.Log
import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.db.entities.Alarm
import com.himanshu.mvvm.services.NotificationController

class AlarmRepository(
    private val db: AppDatabase,
    context: Context
) {

    private val notificationController = NotificationController(context = context)

    suspend fun addAlarm(alarm: Alarm){
        if(alarm.Time < System.currentTimeMillis()){
            Log.d("HIM" ,alarm.eventName + " Cancel")
            return
        }
        Log.d("HIM",alarm.eventName + " Schedule")
        db.getAlarmDao().upsert(alarm)
        notificationController.startAlarm(alarm.Time,alarm.AlarmChannelId,alarm.eventName)
    }

    fun getAlarmChannelId(eventId: String){
        db.getAlarmDao().getAlarmChannelId(eventId)
    }

}