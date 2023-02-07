package com.terabyte.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.terabyte.telegram.R

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_settings_action, menu)
    }
}