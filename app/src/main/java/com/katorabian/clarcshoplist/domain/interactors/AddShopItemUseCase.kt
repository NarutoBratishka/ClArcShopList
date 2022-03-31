package com.katorabian.clarcshoplist.domain.interactors

import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(item: ShopItem) {
        shopListRepository.addShopItem(item)
    }
}