package com.assignment.core.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.liveData
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.usecase.GetProductListUseCase
import com.assignment.core.presentation.base.BaseViewModel

internal class MainViewModel(
    private val productListUseCase: GetProductListUseCase,
    private val resources: Resources
) : BaseViewModel() {

    val productList = liveData {
        uiStateValue.value = UIState.LOADING
        when (val result = productListUseCase.execute()) {
            is NetworkResult.Success -> {
                val list = result.data
                if (!list.isNullOrEmpty()) {
                    uiStateValue.value = UIState.READY
                    emit(list)
                } else {
                    uiStateValue.value = UIState.EMPTY
                }
            }
            is NetworkResult.Error -> {
                errorStateValue.value = resources.getFormattedString(result.error)
            }
        }
    }
}