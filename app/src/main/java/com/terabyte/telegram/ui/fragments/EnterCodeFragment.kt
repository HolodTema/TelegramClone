package com.terabyte.telegram.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.AppTextWatcher
import com.terabyte.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        editRegisterInputCode.addTextChangedListener(AppTextWatcher {
            val string = editRegisterInputCode.text.toString()
            if(string.length==6) {
                verifyCode()
            }
        })
    }

    private fun verifyCode() {
        showToast("OK")
    }
}