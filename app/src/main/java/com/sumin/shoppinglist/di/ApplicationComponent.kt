package com.sumin.shoppinglist.di

import android.app.Application
import com.sumin.shoppinglist.data.ShopListProvider
import com.sumin.shoppinglist.presentation.MainActivity
import com.sumin.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: ShopItemFragment)

    fun inject(provider: ShopListProvider)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}