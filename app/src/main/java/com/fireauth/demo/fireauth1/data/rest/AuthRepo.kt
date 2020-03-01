package com.fireauth.demo.fireauth1.data.rest

import com.fireauth.demo.fireauth1.data.model.LoginModel
import com.fireauth.demo.fireauth1.data.model.SigninResponseModel
import io.reactivex.Single

interface AuthRepo {
    fun signUP(email:String, password:String, key: String): Single<SigninResponseModel>
    fun signIn(email:String, password:String, key: String): Single<SigninResponseModel>
}

class AuthRepoImpl( private val authAPI: AuthAPI) : AuthRepo {

    override fun signIn(email:String, password:String, key: String) =
        authAPI.postSingIn(LoginModel(email, password, true), key)

    override fun signUP(email:String, password:String, key: String): Single<SigninResponseModel>
            = authAPI.postSingUp(LoginModel(email, password, true), key)
}