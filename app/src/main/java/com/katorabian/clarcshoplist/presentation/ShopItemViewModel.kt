package com.katorabian.clarcshoplist.presentation

import androidx.lifecycle.ViewModel
import com.katorabian.clarcshoplist.data.ShopListRepositoryImpl
import com.katorabian.clarcshoplist.domain.AddShopItemUseCase
import com.katorabian.clarcshoplist.domain.EditShopItemUseCase
import com.katorabian.clarcshoplist.domain.GetShopItemUseCase
import com.katorabian.clarcshoplist.domain.ShopItem

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(itemId: Int) {
        getShopItemUseCase.getShopItem(itemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return kotlin.runCatching {
            inputCount?.trim()?.toInt()?: 0
        }.getOrDefault(0)
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) //TODO: show error input name
            result = false
        if (count <= 0) //TODO: show error input count
            result = false

        return result
    }
}