package com.example.mvvmarchitecture.util

import android.content.Context
import android.widget.Toast

internal fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()