package com.himanshu.mvvm.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.ActivityLoginBinding
import com.himanshu.mvvm.util.hide
import com.himanshu.mvvm.util.show
import com.himanshu.mvvm.util.toast

class LoginActivity : AppCompatActivity(),AuthListener {

    private var progressBar:ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.authListener = this

        progressBar = binding.progressBar

    }

    override fun onStarted() {
        if(progressBar != null){
            progressBar?.show()
        }
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this) {
            progressBar?.hide()
            toast(it)
        }
    }

    override fun onFailure(message: String) {
        progressBar?.hide()
        toast(message)
    }
}