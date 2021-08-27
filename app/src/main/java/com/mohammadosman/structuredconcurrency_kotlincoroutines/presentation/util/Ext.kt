package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import android.app.Activity
import android.util.Log
import android.widget.Toast

const val TAG = "AppDebug"

fun logd(msg: String) {
    Log.d(TAG, msg)
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}