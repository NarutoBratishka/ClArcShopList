package com.sumin.shoppinglist.domain

import io.reactivex.rxjava3.disposables.Disposable

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(
        consumer: (data: List<ShopItem>, throwable: Throwable?) -> Unit
    ): Disposable {
        return shopListRepository.getShopList(consumer)
    }
}
