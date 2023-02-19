package com.terabyte.telegram.utilits

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.terabyte.telegram.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if(addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.constraintDataContainer, fragment)
            .commit()
    }
    else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraintDataContainer, fragment)
            .commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.constraintDataContainer, fragment)
        ?.commit()
}

fun hideKeyboard() {
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

//this extension-fun will also work with CircleImage from library, because CircleImage extends ImageView
fun ImageView.downloadAndSetImage(url: String) {
    Picasso.get().load(url)
        .fit() //fit() to avoid some UI crop dreadful stuff
        //here placeholder is default image kinda 'alt' in html
        .placeholder(R.drawable.default_user_photo)
        .into(this)
}
