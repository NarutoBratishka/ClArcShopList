package com.katorabian.clarcshoplist.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.katorabian.clarcshoplist.R
import com.katorabian.clarcshoplist.databinding.FragmentShopItemBinding
import com.katorabian.clarcshoplist.domain.pojos.ShopItem
import com.katorabian.clarcshoplist.presentation.viewModels.ShopItemViewModel

class ShopItemFragment : Fragment() {
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
    get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private var screenMode = MODE_UNKNOWN
    private var shopItemID = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initTextWatchers()
        launchCurrentMode()
        observeViewModels()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchCurrentMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModels() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) getString(R.string.error_input_name) else null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            binding.tilCount.error = if (it) getString(R.string.error_input_count) else null
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun initTextWatchers() {
        binding.edName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }

        binding.edCount.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemID)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            binding.edName.setText(it.name)
            binding.edCount.setText(it.count.toString())
        }

        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(binding.edName.text.toString(), binding.edCount.text.toString())
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(binding.edName.text.toString(), binding.edCount.text.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE))
            throw RuntimeException("Param screen_mode is absent")

        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID))
                throw RuntimeException("Param extra_shop_item_id is absent")

            shopItemID = args.getInt(SHOP_ITEM_ID, -1)
        }
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemID: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemID)
                }
            }
        }
    }
}