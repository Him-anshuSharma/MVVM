package com.himanshu.mvvm.ui.home.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.EventsRepository

class EventViewModelFactory(
    private val eventsRepository: EventsRepository,
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(eventsRepository ) as T
    }
}