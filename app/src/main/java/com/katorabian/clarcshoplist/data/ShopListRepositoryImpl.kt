package com.katorabian.clarcshoplist.data

import com.katorabian.clarcshoplist.domain.ShopItem
import com.katorabian.clarcshoplist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldElem = getShopItem(item.id)
        removeShopItem(oldElem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        }?: throw RuntimeException("Element with id $id not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
    }
}