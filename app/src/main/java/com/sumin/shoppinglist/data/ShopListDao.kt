package com.sumin.shoppinglist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): Single<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel): Completable

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    fun deleteShopItem(shopItemId: Int): Completable

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel
}
