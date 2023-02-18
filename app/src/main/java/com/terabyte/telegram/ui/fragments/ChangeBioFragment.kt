package com.terabyte.telegram.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        edit_settings_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = edit_settings_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO)
            .setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.all_data_has_been_updated))
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }


}