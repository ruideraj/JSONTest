package com.example.jsontest.users

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsontest.R
import com.example.jsontest.ViewModelFactory

class UserDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "user"
    }

    private lateinit var mViewModel : UserDetailsViewModel

    private lateinit var mName : TextView
    private lateinit var mRecycler : RecyclerView
    private lateinit var mAdapter : UserTodoAdapter
    private lateinit var mProgress : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        mViewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(UserDetailsViewModel::class.java)
        val user: User = intent.getParcelableExtra(EXTRA_USER)
        mViewModel.setUser(user)

        mName = findViewById(R.id.user_detail_name)

        mRecycler = findViewById(R.id.user_detail_recycler)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        mRecycler.layoutManager = layoutManager
        mAdapter = UserTodoAdapter()
        mRecycler.adapter = mAdapter

        mProgress = findViewById(R.id.user_detail_progress)

        mViewModel.name.observe(this, Observer { name -> name?.let{ mName.text = name }} )

        mViewModel.listVisible.observe(this, Observer { visible -> visible?.let{
            mRecycler.visibility = if(visible) View.VISIBLE else View.GONE
            mProgress.visibility = if(visible) View.GONE else View.VISIBLE
        } })
        mViewModel.todoList.observe(this, Observer { list -> list?.let{ mAdapter.setData(list) } })
        mViewModel.errorId.observe(this, Observer { errorId -> errorId?.let{
            Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show() } })

    }

    override fun onStart() {
        super.onStart()
        mViewModel.start()
    }
}