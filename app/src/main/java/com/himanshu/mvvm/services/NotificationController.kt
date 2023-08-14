package com.himanshu.mvvm.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*


class NotificationController(val context: Context) {
    fun startAlarm(dateTime:Long,id:Int,eventName: String){
        if(Calendar.getInstance().before(dateTime)){
            return
        }
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlertReceiver::class.java)
        intent.putExtra("EventName",eventName)
        val pendingIntent = PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTime, pendingIntent)
    }

    fun cancelAlarm( id:Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
        alarmManager!!.cancel(pendingIntent)
    }

}