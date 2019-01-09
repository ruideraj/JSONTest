package com.example.jsontest.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jsontest.R

class UsersAdapter(private val viewModel : UsersViewModel) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private var userList : MutableList<User>? = null

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder : ViewHolder, position : Int) {
        val user = userList?.get(position)

        viewHolder.name.text = user?.name
        viewHolder.username.text = user?.username
        viewHolder.email.text = user?.email
    }

    override fun getItemCount() : Int = userList?.size ?: 0

    fun setData(list : MutableList<User>) {
        userList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val name : TextView = view.findViewById(R.id.user_name)
        val username : TextView = view.findViewById(R.id.user_username)
        val email : TextView = view.findViewById(R.id.user_email)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            viewModel.onClick(adapterPosition)
        }
    }
}