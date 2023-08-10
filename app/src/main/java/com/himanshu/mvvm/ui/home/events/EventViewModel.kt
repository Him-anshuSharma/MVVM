package com.himanshu.mvvm.ui.home.events

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.util.ApiException
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.NoInternetException
import com.himanshu.mvvm.util.lazyDeferred

class EventViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    var listener:EventsListener? = null
    var title: String? = null
    var description: String? = null
    var startDateTime: String? = null
    var endDateTime: String? = null
    var location: String? = null

    val events by lazyDeferred {
        repository.getEvents()
    }

    fun getEventByDate(date:String):LiveData<List<Event>>{
        val data = repository.getEventByDate(date)
        return data
    }

    fun navigateToAddEvent(view: View){
        view.findNavController().navigate(R.id.addEventFragment)
    }

    fun goBack(view:View){
        view.findNavController().popBackStack()
    }

    fun deleteEventFromCache(event: Event){
        repository.deleteEventFromCache(event)
    }

    suspend fun deleteEvent(event: Event):Boolean{
        return repository.deleteEvent(event).isSuccessful
    }


    fun addEvent(view: View){
        listener?.onStarted()
        Coroutines.main {
            try{
                val response = repository.addEvent(
                    title!!,
                    description!!,
                    startDateTime!!,
                    endDateTime!!,
                    location!!,
                )
                response.events.let {
                    listener?.onSuccess()
                    Coroutines.IO {
                        repository.saveEvents(it)
                    }
                    return@main
                }
                listener?.onFailure(response.isSuccessful.toString())
            }catch (e: ApiException){
                listener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                listener?.onFailure((e.message!!))
            }
        }
    }
}