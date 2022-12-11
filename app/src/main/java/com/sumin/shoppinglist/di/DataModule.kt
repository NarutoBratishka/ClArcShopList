package com.sumin.shoppinglist.di

import android.app.Application
import com.sumin.shoppinglist.data.AppDatabase
import com.sumin.shoppinglist.data.ShopListDao
import com.sumin.shoppinglist.data.ShopListMapper
import com.sumin.shoppinglist.data.ShopListRepositoryImpl
import com.sumin.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @Provides
        @ApplicationScope
        fun bindShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}