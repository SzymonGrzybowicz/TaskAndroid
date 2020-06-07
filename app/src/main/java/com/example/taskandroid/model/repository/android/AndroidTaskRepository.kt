package com.example.taskandroid.model.repository.android

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.log.Log
import com.example.taskandroid.model.database.TaskDatabase
import com.example.taskandroid.model.dto.Task
import com.example.taskandroid.model.repository.ITaskRepository


class AndroidTaskRepository(private val mDatabase: SQLiteDatabase? = TaskDatabase.database) :
    ITaskRepository {

    override fun fetchAllTasks() {
        Log.d(TAG, "fetchAllTasks()")
        mDatabase?.let { db ->
            val columns = arrayOf(
                TaskDatabase.TASKS_KEY_ID,
                TaskDatabase.TASKS_KEY_NAME,
                TaskDatabase.TASKS_KEY_STATUS
            )
            val cursor = db.query(
                true,
                TaskDatabase.TASKS_TABLE_NAME,
                columns, null, null, null, null,
                null, null
            )

            val tasks = mutableListOf<Task>()
            while (cursor.moveToNext()) {
                tasks.add(
                    Task(
                        cursor.getInt(columns.indexOf(TaskDatabase.TASKS_KEY_ID)),
                        cursor.getString(columns.indexOf(TaskDatabase.TASKS_KEY_NAME)),
                        Task.Status.valueOf(cursor.getString(columns.indexOf(TaskDatabase.TASKS_KEY_STATUS)))
                    )
                )
            }
            cursor.close()
            mTaskLiveData.value = tasks
            return
        }

        mTaskLiveData.value = listOf()
    }

    override fun updateStatus(task: Task) {
        Log.d(TAG, "task($task), updateStatus()")
        mDatabase?.let {
            val contentValues = ContentValues()
            contentValues.put(TaskDatabase.TASKS_KEY_STATUS, task.status.name)
            mDatabase.update(
                TaskDatabase.TASKS_TABLE_NAME,
                contentValues,
                "_id=" + task.id,
                null
            )
        }
    }

    override val tasksLiveData: LiveData<List<Task>>
        get() = mTaskLiveData

    private val mTaskLiveData: MutableLiveData<List<Task>> = MutableLiveData()

    companion object {
        private const val TAG = "AndroidTaskRepository"
    }
}