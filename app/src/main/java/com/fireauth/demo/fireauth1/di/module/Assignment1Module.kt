package com.fireauth.demo.fireauth1.di.module

import android.content.Context
import com.fireauth.demo.fireauth1.di.ActivityScope
import com.fireauth.demo.fireauth1.ui.view.assignment1.Assignment1Activity
import com.fireauth.demo.fireauth1.ui.view.assignment1.Assignment1Interactor
import com.fireauth.demo.fireauth1.ui.view.assignment1.Assignment1ViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
object Assignment1Module {

    @JvmStatic
    @Provides
    @ActivityScope
    fun provideMainActivityViewModel(interactor: Assignment1Interactor) =
        Assignment1ViewModel(interactor)
}

@Component(modules = [Assignment1Module::class])
@ActivityScope
interface Assignment1Component {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun interactor(interactor: Assignment1Interactor): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): Assignment1Component
    }

    fun inject(activity: Assignment1Activity)
}