package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.assignment.core.databinding.FragmentProductRealtimeBinding
import com.assignment.core.presentation.base.BaseFragment
import com.assignment.core.presentation.base.BaseViewModel.UIState
import com.assignment.core.presentation.utils.snackbar
import com.assignment.core.presentation.viewmodel.ProductRealtimeViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ProductRealtimeFragment : BaseFragment() {

    private val viewModel: ProductRealtimeViewModel by stateViewModel()
    private lateinit var binding: FragmentProductRealtimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductRealtimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getString("productId")

        with(viewModel) {
            productId?.let {
                showProduct(productId = productId)
            } ?: return

            tradingProduct.observe(viewLifecycleOwner,
                Observer { product ->
                    product?.let {
                        with(binding) {
                            subtitle.text = product.title
                        }
                    }
                }
            )

            uiState.observe(viewLifecycleOwner,
                Observer { state ->
                    binding.progressBar.visibility = if (state == UIState.LOADING)
                        View.VISIBLE else View.GONE
                    binding.emptyMessage.visibility =
                        if (state == UIState.EMPTY) View.VISIBLE else View.GONE
                })

            errorState.observe(viewLifecycleOwner,
                Observer {
                    binding.root.snackbar(it.message)
                })
        }
    }
}