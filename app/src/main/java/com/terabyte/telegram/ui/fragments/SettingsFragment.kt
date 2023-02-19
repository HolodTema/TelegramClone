package com.terabyte.telegram.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.terabyte.telegram.R
import com.terabyte.telegram.activities.RegisterActivity
import com.terabyte.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
        photoSettingsChangePhoto.setOnClickListener {
            changeUserPhoto()
        }
    }

    private fun changeUserPhoto() {
        CropImage.activity().setAspectRatio(1, 1) //for cropper were like a square with sides a = b
            .setRequestedSize(600, 600) //max size of image, to decrease traffic to server
            .setCropShape(CropImageView.CropShape.OVAL).start(APP_ACTIVITY, this)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_settings_action, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.menu_settings_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            path.putFile(uri).addOnCompleteListener { task1 ->
                if(task1.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { task2 ->
                        if(task2.isSuccessful){
                            val photoUrl = task2.result.toString()
                            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
                                .child(CHILD_PHOTO_URL).setValue(photoUrl)
                                .addOnCompleteListener {
                                    if(it.isSuccessful) {
                                        photoSettingsUser.downloadAndSetImage(photoUrl)
                                        showToast(getString(R.string.all_data_has_been_updated))
                                        USER.photoUrl = photoUrl

                                    }
                                }
                        }
                    }
                }
            }
        }
    }

}