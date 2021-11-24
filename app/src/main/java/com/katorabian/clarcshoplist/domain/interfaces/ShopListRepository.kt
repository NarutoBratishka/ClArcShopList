package com.katorabian.clarcshoplist.domain.interfaces

import androidx.lifecycle.LiveData
import com.katorabian.clarcshoplist.domain.pojos.ShopItem

interface ShopListRepository {

    fun addShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    fun removeShopItem(item: ShopItem)
}