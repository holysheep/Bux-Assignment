package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.core.R
import com.assignment.core.databinding.FragmentProductListBinding
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.infrastructure.ConnectivityOnLifecycleProvider
import com.assignment.core.presentation.databinding.viewBinding
import com.assignment.core.presentation.utils.snackBar
import com.assignment.core.presentation.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

internal class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val viewModel: MainViewModel by stateViewModel()
    private val binding: FragmentProductListBinding by viewBinding()
    private val connectivityProvider: ConnectivityOnLifecycleProvider by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.networkIsAvailable = true
        lifecycleScope.launchWhenStarted {
            connectivityProvider.observeConnectivity { isConnected ->
                binding.networkIsAvailable = isConnected
            }
        }

        val listAdapter = ProductsAdapter(object : ProductAdapterListener {
            override fun onProductClicked(product: TradingProduct) {
                navigateToProduct(product.productId)
            }
        })

        with(binding) {
            list.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    val divider = DividerItemDecoration(
                        list.context,
                        orientation
                    )
                    addItemDecoration(divider)
                }
                adapter = listAdapter
                isNestedScrollingEnabled = false
                setItemViewCacheSize(30)
                setHasFixedSize(true)
            }
        }

        with(viewModel) {
            productList.observe(viewLifecycleOwner,
                Observer { productList ->
                    productList?.let {
                        listAdapter.submitList(it)
                    }
                })

            errorState.observe(viewLifecycleOwner,
                Observer {
                    binding.root.snackBar(it.message)
                })
        }
    }

    private fun navigateToProduct(productId: String) {
        findNavController().navigate(R.id.toProduct, bundleOf(Pair("productId", productId)))
    }
}