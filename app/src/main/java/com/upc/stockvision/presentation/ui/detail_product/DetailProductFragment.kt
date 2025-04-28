package com.upc.stockvision.presentation.ui.detail_product

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailProductBinding
import com.upc.stockvision.databinding.FragmentInventoryControlBinding
import com.upc.stockvision.domain.dto.ProductDTO
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlFragment
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlState
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductFragment @Inject constructor(): BaseFragment<DetailProductViewModel, DetailProductState>() {

    val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding : FragmentDetailProductBinding
    private lateinit var product: ProductDTO

    override fun processRenderState(renderState: DetailProductState, context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(Constants.PRODUCT_KEY) as ProductDTO
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailProductBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        initView()

    }

    fun initView(){

        binding.sCUpdateProduct.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                context?.toast("Hola Checked")
            } else {
                // Acción si el switch está desactivado
            }
        }

        loadProductDetailsData()
        binding.btnBack.setOnClickListener {
//            replaceFragment(inventoryControlFragment)
        }
    }
    private fun removeCurrentFragment() {
        val currentFragment = parentFragmentManager.findFragmentById(R.id.content_frame)
        currentFragment?.let {
            parentFragmentManager.beginTransaction()
                .remove(it)
                .commitNow()
        }
    }
    private fun replaceFragment(fragment: Fragment, args: Bundle? = null) {
        removeCurrentFragment()
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragment.arguments = args
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()
    }
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun loadProductDetailsData() {
        binding.tvTitleNameProduct.text = product.productName
        binding.tvCategory.text = product.categoryName
        binding.tvQuantity.text = product.quantity.toString()
        binding.tvSupplier.text = product.supplierName
        binding.tvWarehouse.text = product.warehouse
        binding.tvAreaWarehouse.text = product.areaWarehouse

        val bitmap = base64ToBitmap(product.photo)
        binding.fondoImagen.setImageBitmap(bitmap)
    }


}