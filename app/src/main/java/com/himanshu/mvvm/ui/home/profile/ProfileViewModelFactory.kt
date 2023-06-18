package com.himanshu.mvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.auth.AuthViewModel

class ProfileViewModelFactory(
    private val repository: UserRepository
    ): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}