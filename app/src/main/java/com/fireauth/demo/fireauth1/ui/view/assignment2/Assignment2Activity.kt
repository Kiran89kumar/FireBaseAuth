package com.fireauth.demo.fireauth1.ui.view.assignment2

import android.os.Bundle
import android.widget.Toast
import com.fireauth.demo.fireauth1.R
import com.fireauth.demo.fireauth1.databinding.ActivityAssignment2Binding
import com.fireauth.demo.fireauth1.di.module.DaggerAssignment2Component
import com.fireauth.demo.fireauth1.ui.view.base.BaseActivity
import javax.inject.Inject

class Assignment2Activity : BaseActivity() ,
    Assignment2Interactor {

    @Inject
    lateinit var viewModel: Assignment2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.let {
            (it as ActivityAssignment2Binding).vm = viewModel
        }
        supportActionBar?.setTitle(R.string.assignment2)
    }

    override fun onStart() {
        super.onStart()
        viewModel.decrypt()
    }

    override fun getLayoutResId(): Int = R.layout.activity_assignment2

    override fun setupDI() {
        DaggerAssignment2Component.builder()
            .interactor(this)
            .context(this)
            .build()
            .inject(this)
    }

    override fun showToastMsg(loginFailed: Int) {
        Toast.makeText(this, loginFailed, Toast.LENGTH_LONG).show()
    }
}
