package com.himanshu.mvvm

import android.app.Application
import com.himanshu.mvvm.data.db.AppDatabase
import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.NetworkConnectionInterceptor
import com.himanshu.mvvm.data.repository.UserRepository
import com.himanshu.mvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MVVMApplication : Application(), DIAware {
    override val di = DI.lazy {
        import(androidXModule(this@MVVMApplication))
        bindSingleton { NetworkConnectionInterceptor(instance()) }
        bindSingleton { MyApi(instance()) }
        bindSingleton { AppDatabase(instance()) }
        bindSingleton { UserRepository(instance(), instance()) }
        bindSingleton { AuthViewModelFactory(instance()) }
    }
}
