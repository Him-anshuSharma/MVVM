package com.himanshu.mvvm.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.home.calendar.CalendarViewModel

class HomeActivityViewModelFactory(
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeActivityViewModel(userRepository) as T
    }
}