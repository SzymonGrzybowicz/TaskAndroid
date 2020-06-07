package com.example.taskandroid.view.activity

import android.os.Bundle
import com.example.base.ui.BaseActivity
import com.example.taskandroid.R

class MainActivity : BaseActivity() {

    override fun getLogTag(): String {
        return "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
