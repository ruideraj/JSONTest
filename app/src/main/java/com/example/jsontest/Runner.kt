package com.example.jsontest

import android.os.Handler
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.Executor

class Runner(private val handler : Handler, private val executor : Executor) {
    fun runUi(runnable : Runnable) = handler.post(runnable)
    fun runBackground(runnable : Runnable) = executor.execute(runnable)

    fun <T> runNetwork(call: Callable<T>, success: Consumer<T>, error: Consumer<Throwable>): Disposable {
        return Observable.fromCallable(call)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success, error)
    }
}