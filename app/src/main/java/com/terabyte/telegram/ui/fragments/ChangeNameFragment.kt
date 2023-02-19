package com.terabyte.telegram.ui.fragments

import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        initFullNameListAndFullNameUI()
    }

    private fun initFullNameListAndFullNameUI() {
        val fullNameList = USER.fullName.split(" ")
        if(fullNameList.size>1) {
            edit_settings_change_name.setText(fullNameList[0])
            edit_settings_change_surname.setText(fullNameList[1])
        }
        else {
            edit_settings_change_name.setText(fullNameList[0])
        }
    }


    override fun change() {
        val name = edit_settings_change_name.text.toString()
        val surname = edit_settings_change_surname.text.toString()
        if(name.isEmpty()) {
            showToast(getString(R.string.name_can_not_be_empty))
        }
        else {
            val fullName = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
                .child(CHILD_FULL_NAME).setValue(fullName)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        showToast(getString(R.string.all_data_has_been_updated))
                        USER.fullName = fullName
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}