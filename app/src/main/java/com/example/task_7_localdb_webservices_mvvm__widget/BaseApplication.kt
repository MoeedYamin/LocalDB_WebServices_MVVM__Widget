package com.example.task_7_localdb_webservices_mvvm__widget

import android.app.Application
import androidx.room.Room
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.AppDatabase

class BaseApplication: Application() {
    companion object {
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, CommonKeys.KEY_DATABASE).build()
    }
}