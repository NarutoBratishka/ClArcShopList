package com.katorabian.clarcshoplist.domain.interactors

import androidx.lifecycle.LiveData
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository
import com.katorabian.clarcshoplist.domain.pojos.ShopItem

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }
}