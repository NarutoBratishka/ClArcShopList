package com.katorabian.clarcshoplist.presentation

import androidx.lifecycle.MutableLiveData
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

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun removeShopItem(item: ShopItem) {
        removeShopListUseCase.removeShopItem(item)
        getShopList()
    }

    fun changeEnabledState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopListUseCase.editShopItem(newItem)
        getShopList()
    }
}