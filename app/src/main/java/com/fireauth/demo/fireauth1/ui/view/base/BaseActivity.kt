package com.fireauth.demo.fireauth1.ui.view.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getLayoutResId() : Int
    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        setupDI()
        if(getLayoutResId() != -1) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutResId(), null, false)
            setContentView(binding?.root)
        }
    }

    open fun setupDI() {}

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }

    fun hideKeyboard() {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }
}