package com.example.taskandroid.view.fragment.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.taskandroid.R
import com.example.taskandroid.model.dto.Task
import kotlinx.android.synthetic.main.task_list_item.view.*
import java.util.*

class TasksAdapter(private val mContext: Context, private val mTasks: List<Task>) :
    ArrayAdapter<Task>(mContext, R.layout.task_list_item, mTasks) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.task_list_item, parent, false)
        val task = mTasks[position]
        listItem.task_name_txt.text = task.name
        listItem.task_id_txt.text = mContext.getString(R.string.id_description, task.id)
        listItem.task_status_txt.text = task.status.name
            .toLowerCase(Locale.getDefault()).capitalize()

        setupButton(listItem.task_status_btn, task)

        val backgroundColorId = when (task.status) {
            Task.Status.OPEN -> android.R.color.holo_green_light
            Task.Status.TRAVELING -> android.R.color.holo_blue_light
            Task.Status.WORKING -> android.R.color.holo_red_light
        }
        listItem.setBackgroundResource(backgroundColorId)
        return listItem
    }

    fun setOnStatusChangeListener(listener: ((task: Task) -> Unit)?) {
        mOnStatusChangeListener = listener
    }

    private fun setupButton(buttonView: Button, task: Task) {
        when (task.status) {
            Task.Status.OPEN -> {
                if (mTasks.any { t -> t.status == Task.Status.TRAVELING || t.status == Task.Status.WORKING }) {
                    buttonView.visibility = View.GONE
                } else {
                    buttonView.visibility = View.VISIBLE
                    buttonView.setText(R.string.start_travel)
                    buttonView.setOnClickListener {
                        task.status = Task.Status.TRAVELING
                        mOnStatusChangeListener?.invoke(task)
                        notifyDataSetChanged()
                    }
                }
            }
            Task.Status.TRAVELING -> {
                buttonView.visibility = View.VISIBLE
                buttonView.setText(R.string.start_work)
                buttonView.setOnClickListener {
                    task.status = Task.Status.WORKING
                    mOnStatusChangeListener?.invoke(task)
                    notifyDataSetChanged()
                }
            }
            Task.Status.WORKING -> {
                buttonView.visibility = View.VISIBLE
                buttonView.setText(R.string.stop_work)
                buttonView.setOnClickListener {
                    task.status = Task.Status.OPEN
                    mOnStatusChangeListener?.invoke(task)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private var mOnStatusChangeListener: ((task: Task) -> Unit)? = null
}