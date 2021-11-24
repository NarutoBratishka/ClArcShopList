package com.katorabian.clarcshoplist.domain.interactors

import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopItem(item: ShopItem) {
        shopListRepository.addShopItem(item)
    }
}