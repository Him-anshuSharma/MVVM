package com.himanshu.mvvm.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.ActivityHomeBinding
import com.himanshu.mvvm.ui.auth.LoginActivity
import com.himanshu.mvvm.util.Coroutines
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class HomeActivity : AppCompatActivity(), DIAware {

    private var toolbar: Toolbar? = null
    private var navView: NavigationView? = null
    private var drawerLayout: DrawerLayout? = null
    private lateinit var viewModel: HomeActivityViewModel


    override val di: DI by closestDI()

    private val factory: HomeActivityViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(owner = this, factory = factory)[HomeActivityViewModel::class.java]

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment?.findNavController()
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        navView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.fragHome -> {
                    navController?.navigate(R.id.profileFragment)
                }
//                R.id.fragEvent -> {
//                    navController?.navigate(R.id.eventFragment)
//                }
//                R.id.fragAddEvent -> {
//                    navController?.navigate(R.id.addEventFragment)
//                }
                R.id.fragCalendar -> {
                    navController?.navigate(R.id.calendarMonthly)
                }
                R.id.logout -> {
                    viewModel.onLogout().let {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                R.id.list_friends -> {
                    navController?.navigate(R.id.friends)
                }
                R.id.add_friend -> {
                    navController?.navigate(R.id.addFriend)
                }
            }

            drawerLayout?.closeDrawers()
            true
        }


        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onResume() {
        super.onResume()
        Coroutines.main {
            viewModel.events.await().observeForever{
                viewModel.setEventsAlarm(it)
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.fragmentContainerView),
            drawerLayout
        )
    }
}