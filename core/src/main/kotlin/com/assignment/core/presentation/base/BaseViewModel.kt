package com.assignment.core.presentation.base

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.assignment.core.domain.error.Failure
import com.assignment.core.presentation.SingleLiveEvent
import com.assignment.core.presentation.utils.networkErrorString

internal open class BaseViewModel : ViewModel() {

    protected val errorValue = SingleLiveEvent<BuxError>()
    val error: LiveData<BuxError> get() = errorValue

    /**
     * Wrapper of error string
     */
    class BuxError(val message: String)

    fun Resources.getFormattedString(error: Failure): BuxError =
        BuxError(error.networkErrorString(this))
}