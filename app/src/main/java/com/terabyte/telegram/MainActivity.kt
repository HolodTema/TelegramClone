package com.terabyte.telegram

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.databinding.ActivityMainBinding
import com.terabyte.telegram.ui.fragments.ChatsFragment
import com.terabyte.telegram.ui.objects.AppDrawer

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if(false) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            supportFragmentManager.beginTransaction()
                .replace(R.id.constraintDataContainer, ChatsFragment())
                .commit()
        }
        else {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initFields() {
        mToolbar = mBinding.toolbarMain
        mAppDrawer = AppDrawer(this, mToolbar)
    }
}