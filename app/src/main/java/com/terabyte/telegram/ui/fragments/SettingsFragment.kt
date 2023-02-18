package com.terabyte.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.terabyte.telegram.MainActivity
import com.terabyte.telegram.R
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.models.User
import com.terabyte.telegram.utilits.AUTH
import com.terabyte.telegram.utilits.USER
import com.terabyte.telegram.utilits.replaceActivity
import com.terabyte.telegram.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        textSettingsBio.text = USER.bio
        textSettingsFullName.text = USER.fullName
        textSettingsPhoneNumber.text = USER.phone
        textSettingsUserStatus.text = USER.status
        textSettingsUsername.text = USER.username
        constraintSettingsChangeLogin.setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }
        constraintSettingsChangeBio.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_settings_action, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.menu_settings_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}