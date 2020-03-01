package com.fireauth.demo.fireauth1.ui.view.assignment2

import android.content.SharedPreferences
import com.fireauth.demo.fireauth1.R
import com.fireauth.demo.fireauth1.TestScheduleProvider
import com.fireauth.demo.fireauth1.data.model.SigninResponseModel
import com.fireauth.demo.fireauth1.data.rest.AuthRepo
import com.fireauth.demo.fireauth1.util.Cryptography
import com.google.firebase.auth.FirebaseUser
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class Assignment2ViewModelTest {

    lateinit var viewModel: Assignment2ViewModel

    @Mock
    lateinit var interactor: Assignment2Interactor

    @Mock
    lateinit var authRepo: AuthRepo

    @Mock
    lateinit var cryptography: Cryptography

    private val preferences: SharedPreferences = mock(SharedPreferences::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)

    private val scheduleProvider = TestScheduleProvider()

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline()})
        MockitoAnnotations.initMocks(this)
        viewModel = Assignment2ViewModel(
            interactor,
            authRepo,
            cryptography,
            preferences,
            scheduleProvider
        )
        `when`(editor.putString(anyString(), ArgumentMatchers.anyString())).thenReturn(editor)
        `when`(editor.clear()).thenReturn(editor)
        `when`(preferences.edit()).thenReturn(editor)
    }

    @Test
    fun updateUINullTest() {
        viewModel.updateUI(null)
        assertTrue(viewModel.signInLayout.get())
        assertFalse(viewModel.welcomeLayout.get())
    }

    @Test
    fun updateUITest() {
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.displayName).thenReturn("Name")
        `when`(mockFirebaseUser.email).thenReturn("Name@demo.com")
        `when`(mockFirebaseUser.isEmailVerified).thenReturn(false)
        val user = SigninResponseModel("localId", "demo@gmail.com", "Demo User", "ID Token", false, "refresh Token", "experies In")
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("Demo User")
            .append("\nEmail:")
            .append("demo@gmail.com")
            .append(" (")
            .append("Not Verified")
            .append(")")
        viewModel.updateUI(user)
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun updateUIVerifiedEmailTest() {
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.displayName).thenReturn("Name")
        `when`(mockFirebaseUser.email).thenReturn("Name@demo.com")
        `when`(mockFirebaseUser.isEmailVerified).thenReturn(false)
        val user = SigninResponseModel("localId", "demo@gmail.com", "Demo User", "ID Token", true, "refresh Token", "experies In")
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("Demo User")
            .append("\nEmail:")
            .append("demo@gmail.com")
            .append(" (")
            .append("Verified")
            .append(")")
        viewModel.updateUI(user)
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun signInClickedErrorTest() {
        viewModel.emailTxt.set("test@gmail.com")
        viewModel.passwordTxt.set("demo@123")
        whenever(authRepo.signIn("test@gmail.com", "demo@123", API_KEY)).thenReturn(Single.error((Exception("Test"))))
        viewModel.signInClicked()
        assertFalse(viewModel.progressVisible.get())
        verify(interactor).showToastMsg(R.string.login_failed)
    }

    @Test
    fun signInClickedTest() {
        viewModel.emailTxt.set("test@gmail.com")
        viewModel.passwordTxt.set("demo@123")
        val response = SigninResponseModel("localid", "test@gmail.com", "display name", "idToken", false, "refreshToken", "expiresIn")
        whenever(authRepo.signIn("test@gmail.com", "demo@123", API_KEY)).thenReturn(Single.just(response))
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("display name")
            .append("\nEmail:")
            .append("test@gmail.com")
            .append(" (")
            .append("Not Verified")
            .append(")")
        viewModel.signInClicked()
        assertFalse(viewModel.progressVisible.get())
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun registerClickedErrorTest() {
        viewModel.emailTxt.set("test@gmail.com")
        viewModel.passwordTxt.set("demo@123")
        whenever(authRepo.signUP("test@gmail.com", "demo@123", API_KEY)).thenReturn(Single.error((Exception("Test"))))
        viewModel.registerClicked()
        assertFalse(viewModel.progressVisible.get())
        verify(interactor).showToastMsg(R.string.registration_failed)
    }

    @Test
    fun registerClickedTest() {
        viewModel.emailTxt.set("test@gmail.com")
        viewModel.passwordTxt.set("demo@123")
        val response = SigninResponseModel("localid", "test@gmail.com", "display name", "idToken", false, "refreshToken", "expiresIn")
        whenever(authRepo.signUP("test@gmail.com", "demo@123", API_KEY)).thenReturn(Single.just(response))
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("display name")
            .append("\nEmail:")
            .append("test@gmail.com")
            .append(" (")
            .append("Not Verified")
            .append(")")
        viewModel.registerClicked()
        assertFalse(viewModel.progressVisible.get())
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun signOutClickedTest() {
        viewModel.signOutClicked()
        assertTrue(viewModel.signInLayout.get())
        assertFalse(viewModel.welcomeLayout.get())
    }

    @Test
    fun isUserNameValidTest() {
        var isValid = viewModel.isUserNameValid("user")
        assertFalse(isValid)

        isValid = viewModel.isUserNameValid("user@")
        assertFalse(isValid)

        isValid = viewModel.isUserNameValid("@user@")
        assertFalse(isValid)

        isValid = viewModel.isUserNameValid("user@.co")
        assertFalse(isValid)

        isValid = viewModel.isUserNameValid("demo@gmail.com")
        assertTrue(isValid)
    }

    @Test
    fun isPasswordValidTest() {
        var isValid = viewModel.isPasswordValid("user")
        assertFalse(isValid)

        isValid = viewModel.isPasswordValid("demo@12")
        assertFalse(isValid)

        isValid = viewModel.isPasswordValid("demo@123")
        assertTrue(isValid)
    }

    @Test
    fun onTextChangeTest() {
        viewModel.emailTxt.set("")
        viewModel.passwordTxt.set("")
        viewModel.onTextChange()
        assertFalse(viewModel.signInEnable.get())

        viewModel.emailTxt.set("sdfsfsdfsdfsd")
        viewModel.passwordTxt.set("a")
        viewModel.onTextChange()
        assertFalse(viewModel.signInEnable.get())

        viewModel.emailTxt.set("demo@gmail.com")
        viewModel.passwordTxt.set("a")
        viewModel.onTextChange()
        assertFalse(viewModel.signInEnable.get())

        viewModel.emailTxt.set("demo@@com")
        viewModel.passwordTxt.set("abcd1234")
        viewModel.onTextChange()
        assertFalse(viewModel.signInEnable.get())

        viewModel.emailTxt.set("demo@gmail.com")
        viewModel.passwordTxt.set("abcd1234")
        viewModel.onTextChange()
        assertTrue(viewModel.signInEnable.get())
    }
}