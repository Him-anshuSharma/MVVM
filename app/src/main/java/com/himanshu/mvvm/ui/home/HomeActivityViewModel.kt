package com.himanshu.mvvm.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.auth.LoginActivity
import com.himanshu.mvvm.util.Coroutines

class HomeActivityViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun onLogout(){
        Coroutines.main {
            userRepository.logout()
        }
    }
}