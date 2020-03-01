package com.fireauth.demo.fireauth1.ui.view.assignment1

import com.fireauth.demo.fireauth1.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class Assignment1ViewModelTest {

    lateinit var viewModel: Assignment1ViewModel

    @Mock
    lateinit var interactor: Assignment1Interactor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = Assignment1ViewModel(interactor)
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
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("Name")
            .append("\nEmail:")
            .append("Name@demo.com")
            .append(" (")
            .append("Not Verified")
            .append(")")
        viewModel.updateUI(mockFirebaseUser)
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun updateUIVerifiedEmailTest() {
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.displayName).thenReturn("Name")
        `when`(mockFirebaseUser.email).thenReturn("Name@demo.com")
        `when`(mockFirebaseUser.isEmailVerified).thenReturn(true)
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("Name")
            .append("\nEmail:")
            .append("Name@demo.com")
            .append(" (")
            .append("Verified")
            .append(")")
        viewModel.updateUI(mockFirebaseUser)
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
    }

    @Test
    fun signInClickedTest() {
        val auth = mock(FirebaseAuth::class.java)
        val mockResult = mock<Task<AuthResult>>().apply {
            whenever(isSuccessful)
                .thenReturn(true)
        }
        `when`(auth.signInWithEmailAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(mockResult)
        viewModel.auth = auth
        viewModel.signInClicked()
        assertTrue(viewModel.progressVisible.get())
        verify(interactor).performAuthCommunication(mockResult)
    }

    @Test
    fun registerClickedTest() {
        val auth = mock(FirebaseAuth::class.java)
        val mockResult = mock<Task<AuthResult>>().apply {
            whenever(isSuccessful)
                .thenReturn(true)
        }
        `when`(auth.createUserWithEmailAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(mockResult)
        viewModel.auth = auth
        viewModel.registerClicked()
        assertTrue(viewModel.progressVisible.get())
        verify(interactor).performAuthCommunication(mockResult)
    }

    @Test
    fun signOutClickedTest() {
        val auth = mock(FirebaseAuth::class.java)
        viewModel.auth = auth
        viewModel.signOutClicked()
        verify(interactor).showToastMsg(R.string.singout_msg)
        assertTrue(viewModel.signInLayout.get())
        assertFalse(viewModel.welcomeLayout.get())
    }

    @Test
    fun notifyUITest() {
        val mockResult = mock<Task<AuthResult>>().apply {
            whenever(isSuccessful)
                .thenReturn(true)
        }
        val auth = mock(FirebaseAuth::class.java)
        viewModel.auth = auth
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.displayName).thenReturn("Name")
        `when`(mockFirebaseUser.email).thenReturn("Name@demo.com")
        `when`(mockFirebaseUser.isEmailVerified).thenReturn(false)
        `when`(auth.currentUser).thenReturn(mockFirebaseUser)
        val userInfo = StringBuilder()
        userInfo.append("Name:")
            .append("Name")
            .append("\nEmail:")
            .append("Name@demo.com")
            .append(" (")
            .append("Not Verified")
            .append(")")
        viewModel.notifyUI(task = mockResult)
        assertTrue(viewModel.welcomeLayout.get())
        assertFalse(viewModel.signInLayout.get())
        assertEquals(userInfo.toString(), viewModel.signInUser.get())
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