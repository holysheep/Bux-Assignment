package com.assignment.core.presentation.utils

import android.view.View
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

// View

fun View.snackbar(message: String) =
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

// Lifecycle

fun Lifecycle.restartingLaunch(
    jobsLifetime: ClosedRange<Lifecycle.Event> = Lifecycle.Event.ON_START..Lifecycle.Event.ON_STOP,
    unsubscribeOn: Lifecycle.Event? = Lifecycle.Event.ON_DESTROY,
    block: suspend CoroutineScope.() -> Unit
) {
    LifecycleScopeRestarter(this, jobsLifetime, unsubscribeOn, block)
}

