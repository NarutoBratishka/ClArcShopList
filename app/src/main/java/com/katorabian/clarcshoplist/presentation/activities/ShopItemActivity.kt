package com.katorabian.clarcshoplist.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.katorabian.clarcshoplist.R
import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.presentation.viewModels.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var edName: TextInputEditText
    private lateinit var edCount: TextInputEditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemID = ShopItem.UNDEFINED_ID

    private lateinit var textWatcher: TextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initView()
        initTextWatchers()
        launchCurrentMode()
        observeViewModels()
    }

    private fun launchCurrentMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModels() {
        viewModel.errorInputName.observe(this) {
            tilName.error = if (it) getString(R.string.error_input_name) else null
        }
        viewModel.errorInputCount.observe(this) {
            tilCount.error = if (it) getString(R.string.error_input_count) else null
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun initTextWatchers() {
        edName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }

        edCount.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemID)
        viewModel.shopItem.observe(this) {
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            viewModel.editShopItem(edName.text.toString(), edCount.text.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(edName.text.toString(), edCount.text.toString())
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("Param screen_mode is absent")

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode
        if (screenMode == MODE_EDIT)
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID))
                throw RuntimeException("Param extra_shop_item_id is absent")

            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
    }

    private fun initView() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        edName = findViewById(R.id.ed_name)
        edCount = findViewById(R.id.ed_count)
        buttonSave = findViewById(R.id.save_button)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, itemId)
            return intent
        }
    }

}