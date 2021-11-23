package com.katorabian.clarcshoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katorabian.clarcshoplist.R
import com.katorabian.clarcshoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            shopListAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with (rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ItemViewType.ENABLED.ordinal,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ItemViewType.DISABLED.ordinal,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        shopListAdapter.apply {
            onShopItemLongClickListener = {
                viewModel.changeEnabledState(it)
            }

            onShopItemClickListener = {
                Log.d("qwe: shopItem.onClick", "$it")
            }
        }
    }
}