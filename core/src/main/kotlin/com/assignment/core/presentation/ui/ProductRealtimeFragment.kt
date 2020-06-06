package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignment.core.R
import com.assignment.core.presentation.viewmodel.ProductRealtimeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductRealtimeFragment : Fragment() {

    private val productViewModel: ProductRealtimeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_realtime, container, false)
    }
}