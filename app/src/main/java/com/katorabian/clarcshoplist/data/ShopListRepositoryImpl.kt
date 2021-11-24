package com.katorabian.clarcshoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.domain.interfaces.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            addShopItem(
                ShopItem("Name $i", i, Random.nextBoolean())
            )
        }
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateList()
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

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}