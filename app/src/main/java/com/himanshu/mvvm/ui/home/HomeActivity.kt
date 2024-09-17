package com.himanshu.mvvm.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.util.Coroutines
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class HomeActivity : AppCompatActivity(), DIAware {

    private var navView: BottomNavigationView? = null
    private lateinit var viewModel: HomeActivityViewModel


    override val di: DI by closestDI()

    private val factory: HomeActivityViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(owner = this, factory = factory)[HomeActivityViewModel::class.java]


        navView = findViewById(R.id.nav_view)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment?.findNavController()

        navView?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.fragCalendar -> {
                    navController?.navigate(R.id.calendarMonthly)
                }

                R.id.list_friends -> {
                    navController?.navigate(R.id.friends)
                }
                R.id.add_friend -> {
                    navController?.navigate(R.id.addFriend)
                }
                R.id.pending_friend_request -> {
                    navController?.navigate(R.id.pendingFriendRequest)
                }
            }

            true
        }

    }

    override fun onResume() {
        super.onResume()
        Coroutines.main {
            viewModel.events.await().observeForever{
                viewModel.setEventsAlarm(it)
            }
        }
    }
}