package com.sumin.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.sumin.shoppinglist.presentation.MainViewModel
import com.sumin.shoppinglist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @ViewModelKey(MainViewModel::class)
    @IntoMap
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @ViewModelKey(ShopItemViewModel::class)
    @IntoMap
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel
}