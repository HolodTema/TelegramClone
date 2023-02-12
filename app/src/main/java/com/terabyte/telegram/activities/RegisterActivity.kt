package com.terabyte.telegram.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.terabyte.telegram.R
import com.terabyte.telegram.databinding.ActivityRegisterBinding
import com.terabyte.telegram.ui.fragments.EnterPhoneNumberFragment
import com.terabyte.telegram.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.toolbarRegister
        setSupportActionBar(mToolbar)
        title = getString(R.string.your_phone)
        replaceFragment(EnterPhoneNumberFragment(), false)
    }
}