package com.himanshu.mvvm.ui.home.events

import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.util.lazyDeferred

class EventViewModel(
    repository: EventsRepository
) : ViewModel() {

    val events by lazyDeferred {
        repository.getEvents()
    }

}