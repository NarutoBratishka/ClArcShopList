package com.katorabian.clarcshoplist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.katorabian.clarcshoplist.R
import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.presentation.helpers.ShopItemDiffCallback

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val resultView = when (viewType) {
            ItemViewType.ENABLED.ordinal -> R.layout.item_shop_enabled
            ItemViewType.DISABLED.ordinal -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            resultView,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        viewHolder.view.apply {
            setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }

            setOnClickListener { onShopItemClickListener?.invoke(shopItem) }
        }

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val resultType =
            if (getItem(position).enabled) ItemViewType.ENABLED
            else ItemViewType.DISABLED

        return resultType.ordinal
    }

    enum class ItemViewType(i: Int) {
        ENABLED(0),
        DISABLED(1)
    }

    companion object {
        const val MAX_POOL_SIZE = 30
    }
}