package com.example.taskandroid.view_model.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskandroid.model.dto.Task
import com.example.taskandroid.model.repository.ITaskRepository
import com.example.taskandroid.model.repository.android.AndroidTaskRepository

class MainFragmentViewModel(
    private val taskRepository: ITaskRepository = AndroidTaskRepository()
) : ViewModel() {

    fun fetchTasks() {
        taskRepository.fetchAllTasks()
    }

    fun onStatusChange(task: Task) {
        taskRepository.updateStatus(task)
    }

    val tasksData: LiveData<List<Task>> = taskRepository.tasksLiveData
}