package com.fireauth.demo.fireauth1.ui.view.assignment1

import android.util.Patterns
import androidx.annotation.VisibleForTesting
import androidx.core.util.PatternsCompat
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.fireauth.demo.fireauth1.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Assignment1ViewModel(
    val interactor: Assignment1Interactor
): ViewModel() {
    val signInLayout = ObservableBoolean(true)
    val welcomeLayout = ObservableBoolean(false)
    val progressVisible = ObservableBoolean(false)
    val signInEnable = ObservableBoolean(false)
    val emailTxt = ObservableField<String>()
    val passwordTxt = ObservableField<String>()
    val signInUser = ObservableField<String>()
    val userInfo = StringBuilder()
    lateinit var auth: FirebaseAuth

    fun updateUI(user: FirebaseUser?) {
        if(user == null) {
            signInLayout.set(true)
            welcomeLayout.set(false)
        } else {
            signInLayout.set(false)
            welcomeLayout.set(true)
            userInfo.setLength(0)
            userInfo.append("Name:")
                .append(user.displayName)
                .append("\nEmail:")
                .append(user.email)
                .append(" (")
                .append(if(user.isEmailVerified ){ "Verified" } else { "Not Verified" })
                .append(")")
            signInUser.set(userInfo.toString())
        }
    }

    fun signInClicked() {
        progressVisible.set(true)
        interactor.performAuthCommunication(auth.signInWithEmailAndPassword(emailTxt.get() ?: "", passwordTxt.get() ?: ""))
    }

    fun registerClicked() {
        progressVisible.set(true)
        interactor.performAuthCommunication(auth.createUserWithEmailAndPassword(emailTxt.get() ?: "", passwordTxt.get() ?: ""))
    }

    fun signOutClicked() {
        auth?.let {
            it.signOut()
            interactor.showToastMsg(R.string.singout_msg)
            updateUI(null)
        }
    }

    fun notifyUI(task: Task<AuthResult>) {
        progressVisible.set(false)
        if (task.isSuccessful) {
            val user = auth.currentUser
            updateUI(user)
        } else {
            interactor.showToastMsg(R.string.login_failed)
        }
    }

    fun onTextChange() {
        if (isUserNameValid(emailTxt.get() ?: "")
            && isPasswordValid(passwordTxt.get() ?: "")) {
            signInEnable.set(true)
        } else {
            signInEnable.set(false)
        }
    }

    @VisibleForTesting
    fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    @VisibleForTesting
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}
