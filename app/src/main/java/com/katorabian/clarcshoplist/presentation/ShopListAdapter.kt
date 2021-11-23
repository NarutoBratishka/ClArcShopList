package com.katorabian.clarcshoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.katorabian.clarcshoplist.R
import com.katorabian.clarcshoplist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        val shopItem = shopList[position]
        viewHolder.view.setOnLongClickListener {
            true
        }

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val resultType =
            if (shopList[position].enabled) ItemViewType.ENABLED
            else ItemViewType.DISABLED

        return resultType.ordinal
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    internal enum class ItemViewType(i: Int) {
        ENABLED(0),
        DISABLED(1)
    }
}