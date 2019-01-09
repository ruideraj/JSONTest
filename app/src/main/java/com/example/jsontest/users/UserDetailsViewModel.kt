package com.example.jsontest.users

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.jsontest.todos.Todo

class UserDetailsViewModel(repository : UserDetailsRepository) : ViewModel() {

    val name : MutableLiveData<String> = MutableLiveData()
    var todoList : MutableLiveData<List<Todo>>
    var errorId : MutableLiveData<Int>

    val listVisible : MediatorLiveData<Boolean> = MediatorLiveData()

    private var mUser : User? = null
    private var mRepository : UserDetailsRepository = repository

    init {
        todoList = mRepository.todoList
        errorId = mRepository.errorId

        listVisible.addSource(todoList) { list -> listVisible.setValue(list != null) }
    }

    fun setUser(user : User) {
        mUser = user
    }

    fun start() {
        if(mUser == null) return

        if(name.value == null) mUser?.let { name.value = it.name }

        if(todoList.value == null) mUser?.let { mRepository.loadUserTodos(it.id) }
    }
}