package com.katorabian.clarcshoplist.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.katorabian.clarcshoplist.data.ShopListRepositoryImpl
import com.katorabian.clarcshoplist.domain.interactors.EditShopItemUseCase
import com.katorabian.clarcshoplist.domain.interactors.GetShopListUseCase
import com.katorabian.clarcshoplist.domain.interactors.RemoveShopItemUseCase
import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item: ShopItem) = scope.launch {
        removeShopListUseCase.removeShopItem(item)
    }

    fun changeEnabledState(item: ShopItem) = scope.launch {
        val newItem = item.copy(enabled = !item.enabled)
        editShopListUseCase.editShopItem(newItem)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}