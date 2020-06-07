package com.assignment.core.presentation.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: String) =
    Snackbar
        .make(this, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

inline fun View.snackBar(message: String, actionText: Int, noinline action: (View) -> Unit) =
    Snackbar
        .make(this, message, Snackbar.LENGTH_SHORT)
        .apply {
            setAction(actionText, action)
            show()
        }
