package com.example.taskandroid.model.dto

data class Task(
    val id: Int,
    var name: String,
    var status: Status
) {

    enum class Status {
        OPEN, TRAVELING, WORKING
    }
}