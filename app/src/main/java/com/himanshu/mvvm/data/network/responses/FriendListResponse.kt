package com.himanshu.mvvm.data.network.responses

import java.io.Serializable

data class FriendListResponse(
    val message: String,
    val friends: List<Friend>
)

data class Friend(
    val _id: String,
    val id: Int,
    val username: String,
):Serializable
