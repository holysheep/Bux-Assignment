package com.assignment.core.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.assignment.core.presentation.SingleLiveEvent

internal open class BaseViewModel : ViewModel() {

    private val _error = SingleLiveEvent<BuxError>()
    val error: LiveData<BuxError> get() = _error

    /**
     * Wrapper of error string
     */
    class BuxError(val error: String)
}