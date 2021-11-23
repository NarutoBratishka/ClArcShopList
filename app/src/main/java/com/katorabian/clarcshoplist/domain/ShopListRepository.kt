package com.katorabian.clarcshoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    fun removeShopItem(item: ShopItem)
}