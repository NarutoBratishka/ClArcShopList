package com.katorabian.clarcshoplist.data

import com.katorabian.clarcshoplist.domain.pojos.ShopItem

class ShopListMapper {

    fun mapEntityToDbModel(shopItem: ShopItem) = shopItem.run {
        ShopItemDbModel(id, name, count, enabled)
    }

    fun mapDbModelToEntity(shopItem: ShopItemDbModel) = shopItem.run {
        ShopItem(name, count, enabled, id)
    }

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}