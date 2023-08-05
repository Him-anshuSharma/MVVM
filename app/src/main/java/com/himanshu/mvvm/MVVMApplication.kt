package com.himanshu.mvvm

import android.app.Application
import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.NetworkConnectionInterceptor
import com.himanshu.mvvm.data.repository.EventsRepository
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.auth.AuthViewModelFactory
import com.himanshu.mvvm.ui.home.calendar.CalendarViewModelFactory
import com.himanshu.mvvm.ui.home.events.EventViewModelFactory
import com.himanshu.mvvm.ui.home.profile.ProfileViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MVVMApplication : Application(), DIAware {
    override val di = DI.lazy {
        import(androidXModule(this@MVVMApplication))
        bindSingleton { NetworkConnectionInterceptor(instance()) }
        bindSingleton { MyApi(instance()) }
        bindSingleton { AppDatabase(instance()) }
        bindSingleton { UserRepository(instance(), instance()) }
        bindSingleton { EventsRepository(instance(),instance())}
        bindSingleton { AuthViewModelFactory(instance()) }
        bindSingleton { ProfileViewModelFactory(instance()) }
        bindSingleton { EventViewModelFactory(instance()) }
        bindSingleton { CalendarViewModelFactory(instance()) }
    }
}
