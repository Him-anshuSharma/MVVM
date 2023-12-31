package com.himanshu.mvvm.data.network.responses

data class PendingFriendRequestsResponse(
    val isSuccessful: Boolean,
    val pendingRequests: List<PendingFriendRequest>
)

data class PendingFriendRequest(
    val _id: String, //requestId
    val senderUserId: Int,
    val receiverUserId: Int,
    val status: String,
    val v:Int
)