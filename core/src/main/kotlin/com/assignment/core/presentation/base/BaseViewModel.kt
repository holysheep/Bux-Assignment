package com.assignment.core.presentation.base

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.core.domain.error.Failure
import com.assignment.core.presentation.SingleLiveEvent
import com.assignment.core.presentation.utils.networkErrorString

internal open class BaseViewModel : ViewModel() {

    protected val errorStateValue = SingleLiveEvent<BuxError>()
    val errorState: LiveData<BuxError> get() = errorStateValue

    protected val uiStateValue = MutableLiveData<UIState>()
    val uiState: LiveData<UIState>
        get() = uiStateValue

    /**
     * Wrapper of error string
     */
    class BuxError(val message: String)

    fun Resources.getFormattedString(error: Failure): BuxError =
        BuxError(error.networkErrorString(this))

    enum class UIState {
        READY,
        LOADING,
        EMPTY
    }
}