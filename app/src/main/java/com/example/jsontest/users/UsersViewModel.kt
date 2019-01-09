package com.example.jsontest.users

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.jsontest.SingleLiveEvent

class UsersViewModel(private val repository : UsersRepository) : ViewModel() {
    var userList : MutableLiveData<MutableList<User>> = repository.userList
    var errorId : MutableLiveData<Int> = repository.error
    var clickedUser : SingleLiveEvent<User> = SingleLiveEvent()

    fun start() {
        if(userList.value == null) {
            repository.loadUserList()
        }
    }

    fun onClick(position: Int) {
        var list = userList.value
        if(list != null && position <= list.size) {
            clickedUser.value = list[position]
        }
    }
}