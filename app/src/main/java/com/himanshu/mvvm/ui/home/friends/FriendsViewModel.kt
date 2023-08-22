package com.himanshu.mvvm.ui.home.friends

import FriendsRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.network.responses.Friend
import com.himanshu.mvvm.data.repository.UserRepository

class FriendsViewModel(
    private val friendsRepository: FriendsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val friends = MutableLiveData<List<Friend>>()
    val user = userRepository.getUser()

    suspend fun getFriendsList(userId: Int) {
        friends.postValue(friendsRepository.getFriendsList(userId).friends)
    }

    suspend fun addFriends(senderId:Int, receiverId: Int) = friendsRepository.sendFriendRequest(senderId,receiverId)

    suspend fun acceptFriendRequest(requestId: String) = friendsRepository.acceptFriendRequest(requestId)

    suspend fun rejectFriendRequest(requestId: String) = friendsRepository.rejectFriendRequest(requestId)

    suspend fun getPendingFriendRequests(userId: Int) = friendsRepository.getPendingFriendRequests(userId)

    suspend fun getUserIdByUsername(username:String) = friendsRepository.getUserIdByUsername(username)

}