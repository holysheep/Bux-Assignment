package com.assignment.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.core.R
import com.assignment.core.databinding.FragmentProductListBinding
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.presentation.base.BaseFragment
import com.assignment.core.presentation.base.BaseViewModel
import com.assignment.core.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import com.assignment.core.presentation.base.BaseViewModel.UIState

internal class ProductListFragment : BaseFragment() {

    private val viewModel: MainViewModel by stateViewModel()
    private lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            uiState.observe(viewLifecycleOwner,
                Observer { state ->
                    binding.progressBar.visibility = if (state == UIState.LOADING)
                        View.VISIBLE else View.GONE
                    binding.emptyMessage.visibility =
                        if (state == UIState.EMPTY) View.VISIBLE else View.GONE
                })
        }
    }

    private fun navigateToProduct(productId: String) {
        findNavController().navigate(R.id.toProduct, bundleOf(Pair("productId", productId)))
    }
}