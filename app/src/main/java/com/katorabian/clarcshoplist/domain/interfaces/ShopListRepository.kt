package com.katorabian.clarcshoplist.domain.interfaces

import androidx.lifecycle.LiveData
import com.katorabian.clarcshoplist.domain.pojos.ShopItem

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun addShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    suspend fun removeShopItem(item: ShopItem)
}