package com.sumin.shoppinglist.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem): Completable

    suspend fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem): Completable

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}
