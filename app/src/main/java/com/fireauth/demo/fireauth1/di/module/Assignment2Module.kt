package com.fireauth.demo.fireauth1.di.module

import android.content.Context
import android.content.SharedPreferences
import com.fireauth.demo.fireauth1.data.rest.AuthRepo
import com.fireauth.demo.fireauth1.di.ActivityScope
import com.fireauth.demo.fireauth1.ui.view.assignment2.Assignment2Activity
import com.fireauth.demo.fireauth1.ui.view.assignment2.Assignment2Interactor
import com.fireauth.demo.fireauth1.ui.view.assignment2.Assignment2ViewModel
import com.fireauth.demo.fireauth1.util.Cryptography
import com.fireauth.demo.fireauth1.util.ScheduleProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

const val SP_MNAME = "com.firebase.auth.demo"

@Module(includes = [AuthAPIModule::class])
object Assignment2Module {

    @JvmStatic
    @Provides
    @ActivityScope
    fun provideViewModel(interactor: Assignment2Interactor, authRepo: AuthRepo, context: Context, sharedPreferences: SharedPreferences, scheduleProvider: ScheduleProvider) =
        Assignment2ViewModel(interactor, authRepo, Cryptography(context), sharedPreferences, scheduleProvider)

    @JvmStatic
    @Provides
    @ActivityScope
    fun providesSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_MNAME, Context.MODE_PRIVATE)
    }

    @JvmStatic
    @Provides
    @ActivityScope
    fun provideScheduler(): ScheduleProvider = ScheduleProvider()
}

@Component(modules = [Assignment2Module::class])
@ActivityScope
interface Assignment2Component {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun interactor(interactor: Assignment2Interactor): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): Assignment2Component
    }

    fun inject(activity: Assignment2Activity)
}