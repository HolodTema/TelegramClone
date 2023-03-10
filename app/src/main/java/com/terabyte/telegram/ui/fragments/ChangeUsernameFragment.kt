package com.terabyte.telegram.ui.fragments

import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {
    lateinit var newUserName: String

    override fun onResume() {
        super.onResume()
        //this method allows fragment to create own menu in actionBar
        edit_settings_username.setText(USER.username)
    }


    override fun change() {
        newUserName = edit_settings_username.text.toString().lowercase(Locale.getDefault())
        if(newUserName.isEmpty()) showToast(getString(R.string.username_can_not_be_empty))
        else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if(it.hasChild(newUserName)) {
                        showToast(getString(R.string.such_username_is_already_busy))
                    }
                    else updateUsernameInNodeUsernames()
                })
        }
    }

    private fun updateUsernameInNodeUsernames() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(newUserName).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    updateUsernameInNodeUsers()
                }
            }
    }

    private fun updateUsernameInNodeUsers() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
            .setValue(newUserName)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    deleteOldUsernameInNodeUsernames()
                }
                else showToast(it.exception?.message.toString())
            }
    }

    private fun deleteOldUsernameInNodeUsernames() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    showToast(getString(R.string.all_data_has_been_updated))
                    fragmentManager?.popBackStack()
                    USER.username = newUserName
                }
                else showToast(it.exception?.message.toString())
            }
    }
}