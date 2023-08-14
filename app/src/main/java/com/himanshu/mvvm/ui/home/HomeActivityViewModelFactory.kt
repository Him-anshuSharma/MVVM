package com.himanshu.mvvm.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.AlarmRepository
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.home.calendar.CalendarViewModel

class HomeActivityViewModelFactory(
    private val userRepository: UserRepository,
    private val alarmRepository: AlarmRepository,
    private val eventsRepository: EventsRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeActivityViewModel(userRepository,alarmRepository,eventsRepository) as T
    }
}