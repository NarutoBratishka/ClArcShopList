package com.katorabian.clarcshoplist.domain.interactors

import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}