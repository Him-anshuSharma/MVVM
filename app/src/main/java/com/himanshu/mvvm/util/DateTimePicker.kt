package com.himanshu.mvvm.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.himanshu.mvvm.R
import java.text.SimpleDateFormat
import java.util.*


class DateTimePicker(private val context: Context) {
    private var onDateTimeSelectedListener: OnDateTimeSelectedListener? = null

    interface OnDateTimeSelectedListener {
        fun onDateTimeSelected(dateTime: String?)
    }

    fun setOnDateTimeSelectedListener(listener: OnDateTimeSelectedListener?) {
        onDateTimeSelectedListener = listener
    }

    fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hourOfDay = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val datePickerDialog = DatePickerDialog(context,
            R.style.PurpleDatePickerDialog,
            { _, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth+1
                val timePickerDialog = TimePickerDialog(context,
                    R.style.PurpleDatePickerDialog,
                    { _, hourOfDay, minute ->
                        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                        calendar[Calendar.MINUTE] = minute
                        val sdf =
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC")
                        val dateTime = sdf.format(calendar.time)
                        if (onDateTimeSelectedListener != null) {
                            onDateTimeSelectedListener!!.onDateTimeSelected(dateTime)
                        }
                    }, hourOfDay, minute, false
                )
                timePickerDialog.show()
            }, year, month, day
        )
        datePickerDialog.show()
    }
}
