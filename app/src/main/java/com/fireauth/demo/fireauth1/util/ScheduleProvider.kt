package com.fireauth.demo.fireauth1.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ScheduleProvider {
    open fun io() : Scheduler = Schedulers.io()
    open fun ui() : Scheduler = AndroidSchedulers.mainThread()
}