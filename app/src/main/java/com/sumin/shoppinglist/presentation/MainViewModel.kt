package com.sumin.shoppinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sumin.shoppinglist.data.ShopListRepositoryImpl
import com.sumin.shoppinglist.domain.DeleteShopItemUseCase
import com.sumin.shoppinglist.domain.EditShopItemUseCase
import com.sumin.shoppinglist.domain.GetShopListUseCase
import com.sumin.shoppinglist.domain.ShopItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private var _shopList = MutableLiveData<List<ShopItem>>()
    val shopList: LiveData<List<ShopItem>>
        get() = _shopList

    private val compositeDisposable = CompositeDisposable()

    fun refreshShopList() {
        val disposable = getShopListUseCase.getShopList { data, throwable ->
            _shopList.postValue(data)
        }
        compositeDisposable.add(disposable)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        val disposable = deleteShopItemUseCase.deleteShopItem(shopItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("MainViewModel", "deleteShopItem")
                refreshShopList()
            }
        compositeDisposable.add(disposable)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        val disposable = editShopItemUseCase.editShopItem(newItem)
            .subscribeOn(Schedulers.io())
            .subscribe {
                refreshShopList()
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
