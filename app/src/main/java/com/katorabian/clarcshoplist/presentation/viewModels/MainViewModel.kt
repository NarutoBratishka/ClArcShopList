package com.katorabian.clarcshoplist.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.katorabian.clarcshoplist.data.ShopListRepositoryImpl
import com.katorabian.clarcshoplist.domain.interactors.EditShopItemUseCase
import com.katorabian.clarcshoplist.domain.interactors.GetShopListUseCase
import com.katorabian.clarcshoplist.domain.interactors.RemoveShopItemUseCase
import com.katorabian.clarcshoplist.domain.pojos.ShopItem

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

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