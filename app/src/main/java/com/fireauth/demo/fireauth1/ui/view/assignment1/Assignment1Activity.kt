package com.fireauth.demo.fireauth1.ui.view.assignment1

import android.os.Bundle
import android.widget.Toast
import com.fireauth.demo.fireauth1.R
import com.fireauth.demo.fireauth1.databinding.ActivityAssignment1Binding
import com.fireauth.demo.fireauth1.di.module.DaggerAssignment1Component
import com.fireauth.demo.fireauth1.ui.view.base.BaseActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class Assignment1Activity : BaseActivity() ,
    Assignment1Interactor {

    @Inject
    lateinit var viewModel: Assignment1ViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel.auth = auth
        binding?.let {
            (it as ActivityAssignment1Binding).vm = viewModel
        }
        supportActionBar?.setTitle(R.string.assignment1)
    }

    override fun getLayoutResId(): Int = R.layout.activity_assignment1

    override fun setupDI() {
        DaggerAssignment1Component.builder()
            .interactor(this)
            .context(this)
            .build()
            .inject(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateUI(auth.currentUser)
    }

    override fun performAuthCommunication(authResult: Task<AuthResult>) {
        authResult.addOnCompleteListener(this) { task ->
            viewModel.notifyUI(task)
        }
    }

    override fun showToastMsg(loginFailed: Int) {
        Toast.makeText(this, loginFailed, Toast.LENGTH_LONG).show()
    }
}
