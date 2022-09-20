package com.katorabian.clarcshoplist.domain.interfaces

import androidx.lifecycle.LiveData
import com.katorabian.clarcshoplist.domain.pojos.ShopItem

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun removeShopItem(item: ShopItem)
}