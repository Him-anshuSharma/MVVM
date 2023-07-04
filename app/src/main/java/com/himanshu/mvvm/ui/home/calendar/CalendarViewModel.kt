package com.himanshu.mvvm.ui.home.calendar

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date

class CalendarViewModel : ViewModel() {
    var calendarCurrentDate: MutableLiveData<String> = MutableLiveData<String>()
    private var date = LocalDate.now()
    private var days:MutableList<String> = mutableListOf()
    private var daysLiveData:MutableLiveData<List<String>> = MutableLiveData()

    init {
        calendarCurrentDate.value = LocalDate.now().month.toString()
        setMonthList()
    }

    fun prevMonth(view: View){
        date = date.minusMonths(1)
        calendarCurrentDate.value = date.month.toString()
        setMonthList()
    }

    fun nextMonth(view:View){
        date =  date.plusMonths(1)
        calendarCurrentDate.value = date.month.toString()
        setMonthList()
    }

    fun getMonthListLiveData(): MutableLiveData<List<String>> {
        return daysLiveData
    }


    private fun setMonthList(){
        days.clear()
        val buffer = LocalDate.of(date.year, date.month, 1).dayOfWeek.value%7
        var totalDays = YearMonth.of(date.year,date.month).lengthOfMonth()
        for(i in 1..buffer){
            days.add("")
        }
        for(i in 1..totalDays){
            days.add(i.toString())
        }
        daysLiveData.postValue(days)
    }
}