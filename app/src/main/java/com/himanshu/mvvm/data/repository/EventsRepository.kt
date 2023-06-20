package com.himanshu.mvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.SafeApiRequest
import com.himanshu.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository(
    private val myApi: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    private val events = MutableLiveData<List<Event>>()

    init{
        events.observeForever{
            saveEvents(it)
        }
    }

    private fun saveEvents(events: List<Event>?) {
        Coroutines.IO {
            db.getEventDao().saveAllEvents(events!!)
        }
    }

    suspend fun getEvents(): LiveData<List<Event>>{
        return withContext(Dispatchers.IO){
            fetchEvents()
            db.getEventDao().getEvents()
        }
    }


    private suspend fun fetchEvents(){
        if(isFetchNeeded()){
            val response = apiRequest { myApi.getEvents() }
            events.postValue(response.events )
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

}