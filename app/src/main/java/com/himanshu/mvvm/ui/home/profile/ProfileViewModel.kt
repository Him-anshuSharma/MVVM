package com.himanshu.mvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.UserRepository

class ProfileViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.getUser()

}