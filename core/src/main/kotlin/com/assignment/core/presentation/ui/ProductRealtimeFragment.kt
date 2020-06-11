package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.assignment.core.R
import com.assignment.core.databinding.FragmentProductRealtimeBinding
import com.assignment.core.domain.provider.LifecycleRegistryProvider
import com.assignment.core.infrastructure.ConnectivityOnLifecycleProvider
import com.assignment.core.presentation.databinding.viewBinding
import com.assignment.core.presentation.utils.snackBar
import com.assignment.core.presentation.viewmodel.ProductRealtimeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ProductRealtimeFragment : Fragment(R.layout.fragment_product_realtime) {

    private val viewModel: ProductRealtimeViewModel by stateViewModel()
    private val binding: FragmentProductRealtimeBinding by viewBinding()
    private val lifecycleProvider: LifecycleRegistryProvider by inject()
    private val connectivityProvider: ConnectivityOnLifecycleProvider by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getString("productId")
        lifecycleProvider.setWebSocketLifecycleObserver(
            lifecycleOwner = this,
            doOnRestart = {
                resubscribeToProduct(productId)
            })

        binding.networkIsAvailable = true
        lifecycleScope.launchWhenStarted {
            connectivityProvider.observeConnectivity { isConnected ->
                binding.networkIsAvailable = isConnected
                resubscribeToProduct(productId)
            }
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        with(viewModel) {
            productId?.let {
                showProduct(productId = productId)
            } ?: return

            errorState.observe(viewLifecycleOwner,
                Observer {
                    binding.root.snackBar(it.message)
                })
        }
    }

    private fun resubscribeToProduct(productId: String?) {
        productId?.let { viewModel.listenForProductUpdate(it) }
    }
}