package com.example.jsontest

import android.os.Handler
import java.util.concurrent.Executor

class Runner(private val handler : Handler, private val executor : Executor) {
    fun runUi(runnable : Runnable) = handler.post(runnable)
    fun runBackground(runnable : Runnable) = executor.execute(runnable)
}