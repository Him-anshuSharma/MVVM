package com.himanshu.mvvm.data.repository

import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.SafeApiRequest
import com.himanshu.mvvm.data.network.responses.AuthResponse

class UserRepository(
    private val api:MyApi,
    private val db: AppDatabase
):SafeApiRequest() {
    suspend fun userLogin(username:String, password:String) : AuthResponse {
        return apiRequest { api.userLogin(username,password) }
    }

    suspend fun userSignup(email:String, username:String, password:String) : AuthResponse {
        return apiRequest { api.userSignUp(email,username,password) }
    }

    suspend fun logout() = db.getUserDao().dropUser()

    fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}