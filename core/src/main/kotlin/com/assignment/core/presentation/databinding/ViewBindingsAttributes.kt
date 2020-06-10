package com.assignment.core.presentation.databinding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter
import com.assignment.core.presentation.base.BaseViewModel.UIState

@BindingAdapter("viewVisibility")
internal fun View.isViewVisible(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("loadingVisibility")
internal fun View.progressBarVisibility(uiState: UIState?) {
    uiState?.let {
        visibility = if (uiState == UIState.LOADING) VISIBLE else GONE
    }
}

@BindingAdapter("emptyVisibility")
internal fun View.emptyTitleVisibility(uiState: UIState?) {
    visibility = if (uiState == null || uiState == UIState.EMPTY) VISIBLE else GONE
}


