package com.fireauth.demo.fireauth1.ui.view.assignment1

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface Assignment1Interactor {
    fun performAuthCommunication(authResult: Task<AuthResult>)
    fun showToastMsg(loginFailed: Int)
}