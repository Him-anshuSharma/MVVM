package com.himanshu.mvvm.ui.home.friends

import FriendsRepository
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.data.repository.UserRepository

class FriendsViewModelFactory(
    private val friendsRepository: FriendsRepository,
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        return FriendsViewModel(friendsRepository,userRepository) as T
    }
}