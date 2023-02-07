package com.terabyte.telegram.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.terabyte.telegram.R
import com.terabyte.telegram.databinding.FragmentEnterPhoneNumberBinding
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        buttonNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if(editRegisterInputPhoneNumber.text.toString().isEmpty()) {
            Toast.makeText(activity, getString(R.string.enter_correct_phone_number), Toast.LENGTH_LONG)
                .show()
        }
        else {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.constraintDataContainer, EnterCodeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}