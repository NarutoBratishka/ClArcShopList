package com.katorabian.clarcshoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katorabian.clarcshoplist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            adapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with (rvShopList) {
            this@MainActivity.adapter = ShopListAdapter()
            adapter = this@MainActivity.adapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ItemViewType.ENABLED.ordinal,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ItemViewType.DISABLED.ordinal,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }
}