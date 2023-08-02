package com.himanshu.mvvm.ui.home.events

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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


    fun addEvent(view: View){
        listener?.onStarted()
        Coroutines.main {
            try{
                val response = repository.addEvent(
                    title!!,
                    description!!,
                    startDateTime!!,
                    endDateTime!!,
                    location!!
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