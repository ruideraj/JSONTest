package com.example.jsontest.users

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.jsontest.R
import com.example.jsontest.ViewModelFactory

class UsersActivity : AppCompatActivity() {
    private lateinit var recycler : RecyclerView
    private lateinit var adapter : UsersAdapter

    private lateinit var viewModel : UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(UsersViewModel::class.java)

        recycler = findViewById(R.id.list_recycler)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.layoutManager = layoutManager
        adapter = UsersAdapter(viewModel)
        recycler.adapter = adapter

        viewModel.userList.observe(this, Observer { list ->
            list?.let { adapter.setData(list) } })
        viewModel.errorId.observe(this, Observer { errorId ->
            errorId?.let { Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show() } })
        viewModel.clickedUser.observe(this, Observer { user ->
            user?.let{
                val intent = Intent(this, UserDetailsActivity::class.java)
                intent.putExtra(UserDetailsActivity.EXTRA_USER, user)
                startActivity(intent)
            } })
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }
}