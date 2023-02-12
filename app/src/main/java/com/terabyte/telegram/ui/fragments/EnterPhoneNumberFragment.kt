package com.terabyte.telegram.ui.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.logTag
import com.terabyte.telegram.utilits.AUTH
import com.terabyte.telegram.utilits.replaceActivity
import com.terabyte.telegram.utilits.replaceFragment
import com.terabyte.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onStart() {
        super.onStart()
        mCallback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    Log.d(logTag, "onVerificationCompleted!")
                    if(task.isSuccessful) {
                        showToast(getString(R.string.welcome))
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    }
                    else showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d(logTag, "onVerificationFailed!")

                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(logTag, "onCodeSent")
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))


            }
        }

        buttonNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if(editRegisterInputPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.enter_correct_phone_number))
        }
        else {
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = editRegisterInputPhoneNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber, 10, TimeUnit.SECONDS, activity as RegisterActivity,
            mCallback
        )
    }
}