package com.fireauth.demo.fireauth1.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.fireauth.demo.fireauth1.R
import com.fireauth.demo.fireauth1.ui.view.assignment1.Assignment1Activity
import com.fireauth.demo.fireauth1.ui.view.assignment2.Assignment2Activity
import com.fireauth.demo.fireauth1.ui.view.base.BaseActivity

class LauncherActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        supportActionBar?.setTitle(R.string.app_title)
    }

    fun initViews() {
        val btnAssign1 = findViewById<Button>(R.id.btn_assign1)
        val btnAssign2 = findViewById<Button>(R.id.btn_assign2)

        btnAssign1.setOnClickListener {
            startActivity(Intent(this, Assignment1Activity::class.java))
        }

        btnAssign2.setOnClickListener {
            startActivity(Intent(this, Assignment2Activity::class.java))
        }
    }
}
