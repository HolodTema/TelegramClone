package com.terabyte.telegram.ui.fragments

import com.terabyte.telegram.R
import com.terabyte.telegram.utilits.APP_ACTIVITY

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.contacts)
    }

}