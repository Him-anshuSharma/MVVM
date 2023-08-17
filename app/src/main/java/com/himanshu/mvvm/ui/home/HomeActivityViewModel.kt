package com.himanshu.mvvm.ui.home

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.db.entities.Alarm
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.repository.AlarmRepository
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.auth.LoginActivity
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.DateToMilli
import com.himanshu.mvvm.util.lazyDeferred

class HomeActivityViewModel(
    private val userRepository: UserRepository,
    private val alarmRepository: AlarmRepository,
    private val eventsRepository: EventsRepository
): ViewModel() {

    private val alarms: MutableList<Alarm> = mutableListOf()

    val events by lazyDeferred {
        eventsRepository.getEvents()
    }


    fun onLogout(){
        Coroutines.main {
            userRepository.logout()
        }
    }

    fun setEventsAlarm(events: List<Event>){
        for(event in events){
            val time:Int = System.currentTimeMillis().toInt()
            alarms.add(
                Alarm(
                    event.title,
                    event._id,
                    time,
                    DateToMilli.convert(event.startDateTime)
                )
            )
            Coroutines.main {
//                Log.d("Di","Scheduled")
                alarmRepository.addAlarm(alarms.last())
            }
        }
    }

}