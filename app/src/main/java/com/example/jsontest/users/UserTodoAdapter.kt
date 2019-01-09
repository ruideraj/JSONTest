package com.example.jsontest.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.jsontest.R
import com.example.jsontest.todos.Todo

class UserTodoAdapter : RecyclerView.Adapter<UserTodoAdapter.ViewHolder>() {

    private var todos: List<Todo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_todo_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val todo = todos?.get(position)

        todo?.let { viewHolder.text.text = it.title }
        todo?.let { viewHolder.checkBox.isChecked = it.completed }
    }

    override fun getItemCount(): Int  = todos?.size ?: 0

    fun setData(list: List<Todo>) {
        todos = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.todo_text)
        val checkBox: CheckBox = view.findViewById(R.id.todo_check)
    }
}