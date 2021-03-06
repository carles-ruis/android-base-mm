package com.carles.core.ui

import android.content.res.Resources
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

inline fun Fragment.consumeMenuClick(f: () -> Unit): Boolean {
    f()
    return true
}

fun AppCompatActivity.getStrings(ids: List<Int>) = ids.map { getString(it) }.toTypedArray()

fun Int.toPx() = this * Resources.getSystem().displayMetrics.density

fun Int.toDp() = this / Resources.getSystem().displayMetrics.density

fun View.setDebounceClickListener(action: () -> Unit, debounceTime: Long = 2000L) {
    var lastClickTime: Long = 0
    this.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            lastClickTime = SystemClock.elapsedRealtime()
            action()
        }
    })
}

fun Fragment.safeNavigate(fragmentId: Int, actionResourceId: Int, bundle: Bundle?) {
    if (findNavController().currentDestination?.id == fragmentId) {
        findNavController().navigate(actionResourceId, bundle)
    }
}