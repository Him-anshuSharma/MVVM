package com.himanshu.mvvm.ui.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.ui.home.events.EventViewModel

class CalendarViewModelFactory(
    private val eventsRepository: EventsRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(eventsRepository) as T
    }
}