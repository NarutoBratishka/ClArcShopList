package com.sumin.shoppinglist.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem): Completable

    fun deleteShopItem(shopItem: ShopItem): Completable

    fun editShopItem(shopItem: ShopItem): Completable

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(
        consumer: (data: List<ShopItem>, throwable: Throwable?) -> Unit
    ): Disposable
}
