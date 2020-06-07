package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.assignment.core.databinding.FragmentProductRealtimeBinding
import com.assignment.core.presentation.viewmodel.ProductRealtimeViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ProductRealtimeFragment : Fragment() {
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
            }

            tradingProduct.observe(viewLifecycleOwner,
                Observer { product ->
                    with(binding) {
                        subtitle.text = product.title
                        currency.text = product.currentPrice.currency
                        price.text = product.currentPrice.amount.toPlainString() // format
                    }
                })

            webSocketState.observe(viewLifecycleOwner,
                Observer { result -> })
        }
    }
}