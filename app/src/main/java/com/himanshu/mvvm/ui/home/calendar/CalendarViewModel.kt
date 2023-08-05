package com.himanshu.mvvm.ui.home.calendar

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.ui.home.events.EventAdapter
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.lazyDeferred
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class CalendarViewModel(
    private val repository: EventsRepository
) : ViewModel() {


    var calendarCurrentDate: MutableLiveData<String> = MutableLiveData<String>()
    private var date = LocalDate.now()
    private var firstDateOfMonth = LocalDate.of(date.year, date.month, 1)
    private var days:MutableList<String> = mutableListOf()
    private var daysLiveData:MutableLiveData<List<String>> = MutableLiveData()
    private var eventsByDate: HashMap<LocalDate,MutableList<Event>> = HashMap()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME


    val events by lazyDeferred {
        repository.getEvents()
    }

    init {
        calendarCurrentDate.value = LocalDate.now().month.toString()
        setMonthList()
    }

    fun navigateToAddEvent(view: View){
        view.findNavController().navigate(R.id.addEventFragment)
    }
    fun getEventsByDate(events: List<Event>): HashMap<LocalDate, MutableList<Event>> {
        eventsByDate.clear()
        for(event in events){
            val offsetDateTime = OffsetDateTime.parse(event.startDateTime, formatter)
            val date = offsetDateTime.toLocalDate()
            if(eventsByDate.get(date) != null){
                eventsByDate[date]?.add(event)
            }
            else{
                eventsByDate[date] = mutableListOf(event)
            }
        }
        return eventsByDate
    }

    fun getCurrDate():LocalDate{
        firstDateOfMonth = LocalDate.of(date.year, date.month, 1)
        return firstDateOfMonth
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

    @RequiresApi(Build.VERSION_CODES.O)
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



