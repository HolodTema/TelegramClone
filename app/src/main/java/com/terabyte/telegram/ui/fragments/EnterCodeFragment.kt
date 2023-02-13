package com.terabyte.telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(private val mPhoneNumber: String, private val id: String) : Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNumber
        editRegisterInputCode.addTextChangedListener(AppTextWatcher {
            val string = editRegisterInputCode.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = editRegisterInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = mPhoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            showToast(getString(R.string.welcome))
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        }
                        else showToast(task2.exception?.message.toString())
                    }


            }
            else showToast(task.exception?.message.toString())
        }
    }
}