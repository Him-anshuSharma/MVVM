package com.himanshu.mvvm.ui.home.friends

import FriendsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.responses.Friend
import com.himanshu.mvvm.data.network.responses.FriendRequestResponse
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.util.ApiException

class FriendsViewModel(
    private val friendsRepository: FriendsRepository,
    userRepository: UserRepository,
) : ViewModel() {

    val friends = MutableLiveData<List<Friend>>()
    val user: LiveData<User>

    init {
        user = userRepository.getUser()
    }

    var username: String? = ""

    suspend fun getFriendsList(userId: Int) {
        friends.postValue(friendsRepository.getFriendsList(userId).friends)
    }

    suspend fun addFriend(senderId: Int): String {
        var response:String = try {
            val receiverId: Int = friendsRepository.getUserIdByUsername(username!!).userId
            friendsRepository.sendFriendRequest(senderId, receiverId).message
        } catch (e:ApiException){
            e.message.toString()
        }
        return response
    }

    suspend fun acceptFriendRequest(requestId: String) =
        friendsRepository.acceptFriendRequest(requestId)

    suspend fun rejectFriendRequest(requestId: String) =
        friendsRepository.rejectFriendRequest(requestId)

    suspend fun getPendingFriendRequests(userId: Int) =
        friendsRepository.getPendingFriendRequests(userId)

    suspend fun getUserIdByUsername(username: String) =
        friendsRepository.getUserIdByUsername(username)

    suspend fun removeFriend(friend: Friend): FriendRequestResponse {
        val response = friendsRepository.removeFriend(friend._id)
        getFriendsList(user.value?.id!!)
        return response
    }

}