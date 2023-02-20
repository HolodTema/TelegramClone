package com.terabyte.telegram

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.databinding.ActivityMainBinding
import com.terabyte.telegram.models.User
import com.terabyte.telegram.ui.fragments.ChatsFragment
import com.terabyte.telegram.ui.objects.AppDrawer
import com.terabyte.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {
            initContacts()
            initFields()
            initFunc()
        }
    }

    private fun initContacts() {
        if(checkPermissions(READ_CONTACTS)) {
            showToast(getString(R.string.reading_contacts))
        }

    }

    private fun initFunc() {
        if(AUTH.currentUser!=null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        }
        else {
            replaceActivity(RegisterActivity())
        }

    }

    private fun initFields() {
        mToolbar = mBinding.toolbarMain
        mAppDrawer = AppDrawer(this, mToolbar)
    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
        REF_DATABASE_ROOT.child(NODE_USERS)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS)== PackageManager.PERMISSION_GRANTED) {
            initContacts()
        }
    }
}