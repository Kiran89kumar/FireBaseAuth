package com.fireauth.demo.fireauth1

import com.fireauth.demo.fireauth1.util.ScheduleProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestScheduleProvider : ScheduleProvider() {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}