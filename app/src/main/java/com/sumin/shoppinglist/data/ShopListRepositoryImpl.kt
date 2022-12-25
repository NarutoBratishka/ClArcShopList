package com.sumin.shoppinglist.data

import android.app.Application
import android.util.Log
import com.sumin.shoppinglist.domain.ShopItem
import com.sumin.shoppinglist.domain.ShopListRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem): Completable {
        return shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem): Completable {
        return shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(
        consumer: (data: List<ShopItem>, throwable: Throwable?) -> Unit
    ): Disposable {
        val disposable = Single.fromCallable { shopListDao.getShopList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: List<ShopItemDbModel>?, throwable: Throwable? ->
                throwable?.let {
                    Log.e(
                        "ShopListRepositoryImpl",
                        "getShopList:: ${Log.getStackTraceString(throwable)}"
                    )
                }?: run {
                    val mappedList = mapper.mapListDbModelToListEntity(result!!)
                    consumer(mappedList, throwable)
                }
            }
        return disposable
    }
}
