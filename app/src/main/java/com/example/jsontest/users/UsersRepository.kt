package com.example.jsontest.users

import androidx.lifecycle.MutableLiveData
import com.example.jsontest.JsonPlaceholderApi
import com.example.jsontest.R
import com.example.jsontest.Runner
import java.io.IOException

class UsersRepository(private val runner : Runner, private val jsonApi : JsonPlaceholderApi) {
    val userList : MutableLiveData<MutableList<User>> = MutableLiveData()
    val error : MutableLiveData<Int> = MutableLiveData()

    fun loadUserList() {
        runner.runBackground(GetUsersTask())
    }

    private inner class GetUsersTask : Runnable {
        override fun run() {
            var users : MutableList<User>? = null
            var errorId : Int? = null

            try {
                users = jsonApi.users.execute().body()
            }
            catch(io : IOException) {
                errorId = R.string.users_error
            }

            val updateTask = UpdateUsersTask(users, errorId)
            runner.runUi(updateTask)
        }
    }

    private inner class UpdateUsersTask(val users : MutableList<User>?, val errorId : Int?) : Runnable {
        override fun run() {
            userList.value = users
            error.value = errorId
        }
    }
}