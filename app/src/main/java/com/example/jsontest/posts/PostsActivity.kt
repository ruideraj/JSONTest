package com.example.jsontest.posts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsontest.R
import com.example.jsontest.ViewModelFactory
import com.example.jsontest.comments.CommentsActivity

class PostsActivity : AppCompatActivity() {

    private var mRecycler: RecyclerView? = null
    private var mAdapter: PostsAdapter? = null

    private var mViewModel: PostsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        mViewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(PostsViewModel::class.java)

        mRecycler = findViewById(R.id.list_recycler)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        mRecycler!!.layoutManager = layoutManager
        mAdapter = PostsAdapter(mViewModel)
        mRecycler!!.adapter = mAdapter

        mViewModel!!.postList.observe(this, Observer { list -> mAdapter!!.setData(list) })

        mViewModel!!.clickedPost.observe(this, Observer { post ->
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra(CommentsActivity.EXTRA_POST, post)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        mViewModel!!.start()
    }
}
