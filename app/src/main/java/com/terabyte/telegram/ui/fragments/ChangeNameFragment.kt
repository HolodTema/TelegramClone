package com.terabyte.telegram.ui.fragments

import android.os.Bundle
import android.provider.Contacts.Intents.UI
import android.view.*
import androidx.fragment.app.Fragment
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : Fragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        val fullNameList = USER.fullName.split(" ")
        edit_settings_change_name.setText(fullNameList[0])
        edit_settings_change_surname.setText(fullNameList[1])
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.menu_settings_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings_confirm_change -> changeNameAndSurname()
        }
        return true
    }

    private fun changeNameAndSurname() {
        val name = edit_settings_change_name.text.toString()
        val surname = edit_settings_change_surname.text.toString()
        if(name.isEmpty()) {
            showToast(getString(R.string.name_can_not_be_empty))
        }
        else {
            val fullName = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
                .child(CHILD_FULL_NAME).setValue(fullName)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        showToast(getString(R.string.all_data_has_been_updated))
                        USER.fullName = fullName
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}