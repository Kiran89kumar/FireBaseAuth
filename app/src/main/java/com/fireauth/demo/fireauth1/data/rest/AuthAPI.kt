package com.fireauth.demo.fireauth1.data.rest

import com.fireauth.demo.fireauth1.data.model.LoginModel
import com.fireauth.demo.fireauth1.data.model.SigninResponseModel
import io.reactivex.Single
import retrofit2.http.*


const val SIGN_UP = "v1/accounts:signUp"
const val SIGN_IN = "v1/accounts:signInWithPassword"

interface AuthAPI {

    @POST(SIGN_UP)
    @Headers("Content-Type: application/json")
    fun postSingUp(@Body body: LoginModel, @Query("key") key: String): Single<SigninResponseModel>

    @POST(SIGN_IN)
    @Headers("Content-Type: application/json")
    fun postSingIn(@Body body: LoginModel, @Query("key") key: String): Single<SigninResponseModel>

}