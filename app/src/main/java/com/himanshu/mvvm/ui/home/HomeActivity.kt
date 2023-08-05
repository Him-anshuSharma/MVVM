package com.himanshu.mvvm.ui.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.ui.home.profile.ProfileViewModel
import com.himanshu.mvvm.util.toast

class HomeActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var navView: NavigationView? = null
    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                    navController?.navigate(R.id.calendarFragment)
                }
            }

            drawerLayout?.closeDrawers()
            true
        }


        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.fragmentContainerView),
            drawerLayout
        )
    }
}