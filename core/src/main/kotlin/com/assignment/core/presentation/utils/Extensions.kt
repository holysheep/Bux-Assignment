package com.assignment.core.presentation.utils

import android.graphics.Color
import android.text.style.CharacterStyle
import android.view.View
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

// View

fun View.snackBar(message: String) =
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .apply { show() }

inline fun View.snackBar(
    message: String,
    actionText: Int,
    noinline action: (View) -> Unit
) =
    Snackbar
        .make(this, message, Snackbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
                .setActionTextColor(Color.CYAN)
            show()
        }





