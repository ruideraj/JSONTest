package com.example.jsontest.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        layoutManager.orientation = RecyclerView.VERTICAL
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