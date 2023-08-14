package com.himanshu.mvvm.util

import java.text.SimpleDateFormat
import java.util.*

class DateToMilli {
    companion object{
        fun convert(dateTimeString: String):Long {
            var milliseconds = 0L
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val date: Date = sdf.parse(dateTimeString)
                milliseconds = date.time
                println("Milliseconds since epoch: $date")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return milliseconds
        }
    }

}