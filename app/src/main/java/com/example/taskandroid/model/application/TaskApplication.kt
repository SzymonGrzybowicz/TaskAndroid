package com.example.taskandroid.model.application

import android.app.Application
import com.example.base.log.Log
import com.example.taskandroid.model.database.TaskDatabase

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
        TaskDatabase.open(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
        TaskDatabase.release()
    }

    companion object {
        private const val TAG = "TaskApplication"
    }
}