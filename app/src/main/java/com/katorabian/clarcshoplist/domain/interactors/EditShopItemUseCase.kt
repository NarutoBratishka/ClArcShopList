package com.katorabian.clarcshoplist.domain.interactors

import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(item: ShopItem) {
        shopListRepository.editShopItem(item)
    }
}