package com.example.jsontest.users

import androidx.lifecycle.MutableLiveData
import com.example.jsontest.JsonPlaceholderApi
import com.example.jsontest.R
import com.example.jsontest.Runner
import com.example.jsontest.SingleLiveEvent
import com.example.jsontest.todos.Todo
import retrofit2.Response
import java.io.IOException

class UserDetailsRepository(private val runner : Runner, private val jsonApi : JsonPlaceholderApi) {

    val todoList : MutableLiveData<List<Todo>> = MutableLiveData()
    val errorId : SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadUserTodos(userId : Int) {
        runner.runBackground(GetUserTodosTask(userId))
    }

    private inner class GetUserTodosTask(val userId: Int) : Runnable {

        override fun run() {
            val todosCall = jsonApi.getUserTodos(userId)
            var list : List<Todo>? = null
            var e : Exception? = null

            try {
                val response : Response<List<Todo>> = todosCall.execute()
                if(response.isSuccessful) list = response.body()
            }
            catch(io : IOException) {
                e = io
            }

            runner.runUi(UpdateUserTodosTask(list, e))
        }
    }

    private inner class UpdateUserTodosTask(val list: List<Todo>?, val e: Exception?): Runnable{
        override fun run() {
            list?.let { todoList.value = it }
            e?.let { errorId.value = R.string.user_details_todos_error }
        }
    }
}