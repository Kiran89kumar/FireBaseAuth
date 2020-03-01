package com.fireauth.demo.fireauth1.di.module

import android.content.SharedPreferences
import com.fireauth.demo.fireauth1.data.rest.AuthAPI
import com.fireauth.demo.fireauth1.data.rest.AuthRepo
import com.fireauth.demo.fireauth1.data.rest.AuthRepoImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
object AuthAPIModule {

    private val BASE_URL = "https://identitytoolkit.googleapis.com/"

    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmStatic
    @Provides
    fun provideApiService(retrofit: Retrofit): AuthAPI = retrofit.create(AuthAPI::class.java)

    @JvmStatic
    @Provides
    fun provideAuthRepo(authAPI: AuthAPI): AuthRepo = AuthRepoImpl(authAPI)
}