package com.terabyte.telegram.ui.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.logTag
import com.terabyte.telegram.utilits.AUTH
import com.terabyte.telegram.utilits.AppTextWatcher
import com.terabyte.telegram.utilits.replaceActivity
import com.terabyte.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(private val mPhoneNumber: String, private val id: String) : Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart in EnterCodeFragment()")


        (activity as RegisterActivity).title = mPhoneNumber
        editRegisterInputCode.addTextChangedListener(AppTextWatcher {
            val string = editRegisterInputCode.text.toString()
            if(string.length==6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = editRegisterInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            Log.d(logTag, "OnCompleteListener for signInWithCredential")

            if(task.isSuccessful) {
                showToast(getString(R.string.welcome))
                (activity as RegisterActivity).replaceActivity(MainActivity())
            }
            else showToast(task.exception?.message.toString())
        }
    }
}