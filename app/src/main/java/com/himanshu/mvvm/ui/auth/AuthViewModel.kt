package com.himanshu.mvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.util.ApiException
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var username: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null

    var authListener:AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email or Password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!,password!!)
                authResponse.user.let {
                    authListener?.onSuccess(it)
                    Coroutines.IO {
                        repository.saveUser(it)
                    }
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e:NoInternetException){
                authListener?.onFailure((e.message!!))
            }
        }
    }

    fun onSignup(view: View){
        Intent(view.context,SignupActivity::class.java).let {
            view.context.startActivity(it)
        }
    }

    fun onSignupButtonClicked(view:View) {
        authListener?.onStarted()
        if (username.isNullOrEmpty()) {
            authListener?.onFailure("Username Required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email Required")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Password")
            return
        }
        if (password != confirmPassword) {
            authListener?.onFailure("Password Doesn't match")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userSignup(email!!,username!!,password!!)
                authResponse.user.let {
                    authListener?.onSuccess(it)
                    Coroutines.IO {
                        repository.saveUser(it)
                    }
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e:NoInternetException){
                authListener?.onFailure((e.message!!))
            }
        }
    }
}
//2023-06-20T09:00:00.000Z