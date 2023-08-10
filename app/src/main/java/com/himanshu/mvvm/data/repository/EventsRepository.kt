package com.himanshu.mvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.SafeApiRequest
import com.himanshu.mvvm.data.network.responses.EventResponse
import com.himanshu.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository(
    private val myApi: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    private var uid: Int? = -1
    private val events = MutableLiveData<List<Event>>()
    private var user: LiveData<User> = db.getUserDao().getUser()

    init {

        events.observeForever {
            saveEvents(it)
        }

        user.observeForever {
            uid = it?.id

        }
    }

    suspend fun deleteEvent(event: Event):EventResponse{
        return apiRequest {
            myApi.deleteEvent(
                event.title,
                event.description,
                event.startDateTime,
                event.endDateTime,
                event.location,
            )
        }
    }

    fun getEventByDate(date:String):LiveData<List<Event>>{
        return db.getEventDao().getEventByDate(date)
    }

    fun saveEvents(events: List<Event>?) {
        Coroutines.IO {
            db.getEventDao().deleteEvents()
            db.getEventDao().saveAllEvents(events!!)
        }
    }

    fun updateEvents(newEvents: List<Event>){
        events.postValue(newEvents)
    }
    suspend fun getEvents(): LiveData<List<Event>> {
        return withContext(Dispatchers.IO) {
            fetchEvents()
            db.getEventDao().getEvents()
        }
    }

    suspend fun addEvent(
        title: String,
        description: String,
        startDate: String,
        endDate: String,
        location: String,
    ): EventResponse {
        return apiRequest {
            myApi.addEvent(
                title,
                description,
                startDate,
                endDate,
                location,
                uid!!
            )
        }
    }

    private suspend fun fetchEvents() {
        if (isFetchNeeded()) {
            if (uid != -1) {
                val response = apiRequest { myApi.getEvents(user.value?.id) }
                events.postValue(response.events)
            }
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

}