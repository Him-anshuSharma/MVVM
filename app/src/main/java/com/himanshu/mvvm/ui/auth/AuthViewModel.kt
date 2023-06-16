package com.himanshu.mvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.util.ApiException
import com.himanshu.mvvm.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener:AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClicked(view:View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email or Password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    Coroutines.IO {
                        repository.saveUser(it)
                    }
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e:ApiException){
                authListener?.onFailure(e.message!!)
            }
        }
    }
}