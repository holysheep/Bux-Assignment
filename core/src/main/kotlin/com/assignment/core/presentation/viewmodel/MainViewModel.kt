package com.assignment.core.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.liveData
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.usecase.GetProductListUseCase
import com.assignment.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

internal class MainViewModel(
    private val productListUseCase: GetProductListUseCase,
    private val resources: Resources
) : BaseViewModel() {

    val productList = liveData {
        productListUseCase
            .execute()
            .onStart { uiState.value = UIState.LOADING }
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val list = result.data
                        if (!list.isNullOrEmpty()) {
                            uiState.value = UIState.READY
                            emit(list)
                        } else {
                            uiState.value = UIState.EMPTY
                        }
                    }
                    is NetworkResult.Error -> {
                        errorStateValue.value = resources.getFormattedString(result.error)
                    }
                }
            }
    }
}