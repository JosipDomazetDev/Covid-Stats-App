package com.covidstats.covidstats.utility


import android.R
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.navigation.NavigationView
import java.util.*


class UserUtility private constructor() {
    companion object {

        fun hideKeyboard(activity: Activity?) {
            if (activity == null) return

            //https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
            val imm =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus

            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // Private constructor to prevent instantiation
    init {
        throw UnsupportedOperationException()
    }
}
