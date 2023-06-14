package com.himanshu.mvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    suspend fun userLogin(username:String, password:String) : Response<AuthResponse> {
        return MyApi().userLogin(username,password)
    }

}