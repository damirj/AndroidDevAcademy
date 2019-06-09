package osc.androiddevacademy.movieapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this).load(IMAGE_URL + imageUrl).into(this)
}

fun FragmentActivity.showFragment(containerId: Int, fragment: Fragment, shouldAddToBackStack: Boolean = false, tag: String = ""){
    supportFragmentManager.beginTransaction().apply {
        if(shouldAddToBackStack){
            addToBackStack(tag)
        }
    }.replace(containerId, fragment).commitAllowingStateLoss()
}

fun Context.displayToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
