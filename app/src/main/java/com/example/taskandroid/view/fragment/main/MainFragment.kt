package com.example.taskandroid.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.base.log.Log
import com.example.base.ui.BaseFragment
import com.example.taskandroid.R
import com.example.taskandroid.model.dto.Task
import com.example.taskandroid.view_model.fragment.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun getLogTag(): String {
        return "MainFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, null)
    }

    override fun onStart() {
        super.onStart()
        mAdapter = TasksAdapter(requireContext(), mTasks)
        mAdapter?.setOnStatusChangeListener {
            mViewModel.onStatusChange(it)
        }
        tasks_list.adapter = mAdapter

        mViewModel.tasksData.observe(this, Observer {
            updateTasksList(it)
        })
        mViewModel.fetchTasks()
    }

    private fun updateTasksList(tasks: List<Task>) {
        Log.d(getLogTag(), "tasks($tasks), showTasks()")
        mTasks.clear()
        mTasks.addAll(tasks)
        mAdapter?.notifyDataSetChanged()
    }

    private val mViewModel: MainFragmentViewModel by viewModels()
    private var mTasks = mutableListOf<Task>()
    private var mAdapter: TasksAdapter? = null
}