package com.limesplash.breakingbadexample.util

import io.reactivex.Observable
import io.reactivex.Observer

open class ObservableDelegate<T>: Observable<T>() {

    private var observer: Observer<in T>? = null

    override fun subscribeActual(observer: Observer<in T>?) {
        this.observer = observer
    }

    fun getLambda():(it: T) -> Unit = {
        observer?.onNext(it)
    }

}