package com.himanshu.mvvm.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.databinding.ActivitySignupBinding
import com.himanshu.mvvm.ui.home.HomeActivity
import com.himanshu.mvvm.util.hide
import com.himanshu.mvvm.util.show
import com.himanshu.mvvm.util.snackbar
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class SignupActivity : AppCompatActivity(),AuthListener,DIAware {

    private var progressBar: ProgressBar? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    override val di: DI by closestDI()
    private val factory:AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        val viewModel = ViewModelProvider(this,factory)[AuthViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer {
            if(it!=null){
                Intent(this, HomeActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        })

        progressBar = binding.progressBar
        coordinatorLayout = binding.rootLayoutSignup

    }

    override fun onStarted() {
        if(progressBar!=null) {
            progressBar?.show()
        }
    }

    override fun onSuccess(user: User) {
        progressBar?.hide()
        coordinatorLayout?.snackbar("${user.username} SignUp Successful!")
    }

    override fun onFailure(message: String) {
        progressBar?.hide()
        coordinatorLayout?.snackbar(message)
    }
}