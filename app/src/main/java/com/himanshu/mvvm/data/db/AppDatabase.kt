package com.himanshu.mvvm.data.db

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.db.entities.User


@Database(
    entities = [User::class,Event::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getEventDao(): EventDao
    companion object{
        @Volatile
        private var instance:AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        ).build()
    }
}