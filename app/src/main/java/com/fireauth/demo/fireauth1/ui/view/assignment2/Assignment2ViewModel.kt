package com.fireauth.demo.fireauth1.ui.view.assignment2

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.core.util.PatternsCompat
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.fireauth.demo.fireauth1.R
import com.fireauth.demo.fireauth1.data.model.SigninResponseModel
import com.fireauth.demo.fireauth1.data.rest.AuthRepo
import com.fireauth.demo.fireauth1.util.Cryptography
import com.fireauth.demo.fireauth1.util.ScheduleProvider
import io.reactivex.Observable

const val API_KEY = "AIzaSyB_6cMTd1IE5Hax09unN7r5Yuueied-0_4"
const val SP_EMAIL ="com.sp.email"
const val SP_PASSWORD ="com.sp.password"
const val SP_TOKEN ="com.sp.token"

class Assignment2ViewModel(
    private val interactor: Assignment2Interactor,
    private val authRepo: AuthRepo,
    private val cryptography: Cryptography,
    private val sharedPreferences: SharedPreferences,
    private val scheduleProvider: ScheduleProvider
): ViewModel() {
    val signInLayout = ObservableBoolean(true)
    val welcomeLayout = ObservableBoolean(false)
    val progressVisible = ObservableBoolean(false)
    val signInEnable = ObservableBoolean(false)
    val emailTxt = ObservableField<String>()
    val passwordTxt = ObservableField<String>()
    val signInUser = ObservableField<String>()
    val userInfo = StringBuilder()

    @VisibleForTesting
    fun updateUI(user: SigninResponseModel?) {
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
                .append(if(user.registered){ "Verified" } else { "Not Verified" })
                .append(")")
            signInUser.set(userInfo.toString())
            encryptThenSave(user)
        }
    }

    @VisibleForTesting
    fun encryptThenSave(user: SigninResponseModel) {
        val list = listOf(SP_EMAIL, SP_PASSWORD, SP_TOKEN)
        Observable.fromIterable(list)
            .subscribe({
                val data: String = when(it){
                    SP_EMAIL -> {
                        cryptography.encryptData(user.email)
                    }
                    SP_PASSWORD -> {
                        cryptography.encryptData(passwordTxt.get())
                    }
                    else -> {
                        cryptography.encryptData(user.idToken)
                    }
                }
                sharedPreferences.edit().putString(it, data).apply()
            }, {
                interactor.showToastMsg(R.string.session_failed)
            })
    }

    @VisibleForTesting
    fun decrypt() {
        val list = listOf(SP_EMAIL, SP_PASSWORD, SP_TOKEN)
        Observable.fromIterable(list)
            .subscribe({
                when(it){
                    SP_EMAIL -> {
                        emailTxt.set(cryptography.decryptData(sharedPreferences.getString(it, "")))
                    }
                    SP_PASSWORD -> {
                        passwordTxt.set(cryptography.decryptData(sharedPreferences.getString(it, "")))
                    }
                }
                signInClicked()
            }, {
                //do nothing
            })
    }

    @VisibleForTesting
    fun signInClicked() {
        val email = emailTxt.get() ?: ""
        val password = passwordTxt.get() ?: ""
        if(email.isNotBlank() && password.isNotBlank()) {
            authRepo.
                signIn(email, password, API_KEY)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe {progressVisible.set(true) }
                .subscribe({
                    updateUI(it)
                    progressVisible.set(false)
                }, {
                    progressVisible.set(false)
                    interactor.showToastMsg(R.string.login_failed)
                })
        }
    }

    @VisibleForTesting
    fun registerClicked() {
        val email = emailTxt.get() ?: ""
        val password = passwordTxt.get() ?: ""
        if(email.isNotBlank() && password.isNotBlank()) {
            authRepo.
                signUP(email, password, API_KEY)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe {progressVisible.set(true) }
                .subscribe({
                    progressVisible.set(false)
                    updateUI(it)
                }, {
                    progressVisible.set(false)
                    interactor.showToastMsg(R.string.registration_failed)
                })
        }
    }

    @VisibleForTesting
    fun signOutClicked() {
        sharedPreferences.edit().clear().apply()
        emailTxt.set("")
        passwordTxt.set("")
        updateUI(null)
    }

    @VisibleForTesting
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
