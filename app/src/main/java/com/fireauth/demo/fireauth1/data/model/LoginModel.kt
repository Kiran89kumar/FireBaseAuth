package com.fireauth.demo.fireauth1.data.model

class LoginModel (
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)

class SigninResponseModel (
    val localId: String,
    val email: String,
    val displayName: String,
    val idToken: String,
    val registered: Boolean,
    val refreshToken: String,
    val expiresIn: String
)