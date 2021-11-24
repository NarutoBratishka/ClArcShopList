package com.katorabian.clarcshoplist.domain.interactors

import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun removeShopItem(item: ShopItem) {
        shopListRepository.removeShopItem(item)
    }
}