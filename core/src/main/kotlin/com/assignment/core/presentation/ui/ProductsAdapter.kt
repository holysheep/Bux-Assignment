package com.assignment.core.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.core.databinding.ItemProductBinding
import com.assignment.core.domain.model.retreive.TradingProduct

internal class ProductsAdapter(private val listener: ProductAdapterListener) :
    ListAdapter<TradingProduct, ProductsAdapter.TradingProductViewHolder>(ProductItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradingProductViewHolder =
        TradingProductViewHolder.create(parent)

    override fun onBindViewHolder(holder: TradingProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener.onProductClicked(item) }
    }

    class TradingProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: TradingProduct) {
            binding.name.text = product.title
        }

        companion object {
            fun create(parent: ViewGroup): TradingProductViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                return TradingProductViewHolder(binding)
            }
        }
    }

    private companion object {
        private object ProductItemDiffCallback : DiffUtil.ItemCallback<TradingProduct>() {
            override fun areItemsTheSame(
                oldItem: TradingProduct,
                newItem: TradingProduct
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TradingProduct,
                newItem: TradingProduct
            ): Boolean =
                oldItem.symbol == newItem.symbol
        }
    }
}