package com.example.taskandroid.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.base.log.Log
import com.example.taskandroid.BuildConfig

class TaskDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate()")
        db.beginTransaction()
        db.execSQL(CREATE_TABLE_QUERY)
        db.setTransactionSuccessful()
        db.endTransaction()

        if (BuildConfig.DEBUG) {
            fillTestData(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun fillTestData(db: SQLiteDatabase) {
        Log.d(TAG, "fillTestData()")
        db.beginTransaction()
        for (i in 0..20) {
            db.execSQL(
                "INSERT INTO $TASKS_TABLE_NAME($TASKS_KEY_NAME)" +
                        "VALUES('TEST TASK NO. $i')"
            )
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    companion object {
        var database: SQLiteDatabase? = null
            private set

        fun open(context: Context) {
            if (database == null)
                database = TaskDatabase(context).writableDatabase
        }

        fun release() {
            if (database != null) {
                database!!.close()
                database = null
            }
        }

        const val TASKS_TABLE_NAME = "TASKS"
        const val TASKS_KEY_ID = "_id"
        const val TASKS_KEY_NAME = "name"
        const val TASKS_KEY_STATUS = "status"

        private const val TAG = "TaskDatabase"
        private const val DATABASE_NAME = "task_database"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TASKS_TABLE_NAME (" +
                "$TASKS_KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$TASKS_KEY_NAME TEXT NOT NULL," +
                "$TASKS_KEY_STATUS TEXT NOT NULL DEFAULT 'OPEN');"


    }
}