package com.himanshu.mvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.util.Coroutines

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener:AuthListener? = null

    fun onLoginButtonClicked(view:View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email or Password")
            return
        }
        Coroutines.main {
            val loginResponse = UserRepository().userLogin(email!!, password!!)
            if(loginResponse.isSuccessful){
                authListener?.onSuccess(loginResponse.body()?.user!!)
            }
            else{
                authListener?.onFailure(loginResponse.code().toString())
            }
        }
    }
}