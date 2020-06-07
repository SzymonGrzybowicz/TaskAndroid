package com.example.taskandroid.model.repository

import androidx.lifecycle.LiveData
import com.example.taskandroid.model.dto.Task

interface ITaskRepository {

    fun fetchAllTasks()
    fun updateStatus(task: Task)

    val tasksLiveData: LiveData<List<Task>>
}