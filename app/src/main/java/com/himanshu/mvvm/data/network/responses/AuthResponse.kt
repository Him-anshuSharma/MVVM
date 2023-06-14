package com.himanshu.mvvm.data.network.responses

import com.himanshu.mvvm.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val user: User,
    val message: String?
)