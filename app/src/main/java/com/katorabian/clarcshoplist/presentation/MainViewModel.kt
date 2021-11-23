package com.katorabian.clarcshoplist.presentation

import androidx.lifecycle.ViewModel
import com.katorabian.clarcshoplist.data.ShopListRepositoryImpl
import com.katorabian.clarcshoplist.domain.EditShopItemUseCase
import com.katorabian.clarcshoplist.domain.GetShopListUseCase
import com.katorabian.clarcshoplist.domain.RemoveShopItemUseCase
import com.katorabian.clarcshoplist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item: ShopItem) {
        removeShopListUseCase.removeShopItem(item)
    }

    fun changeEnabledState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopListUseCase.editShopItem(newItem)
    }
}