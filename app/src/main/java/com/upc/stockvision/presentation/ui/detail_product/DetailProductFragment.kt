package com.upc.stockvision.presentation.ui.detail_product

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailProductBinding
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.DialogAreaWarehouse
import com.upc.stockvision.presentation.dialog.DialogCategory
import com.upc.stockvision.presentation.dialog.DialogSupplier
import com.upc.stockvision.presentation.dialog.DialogWarehouse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductFragment @Inject constructor(val appState: AppState): BaseFragment<DetailProductViewModel, DetailProductState>() {

    val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding : FragmentDetailProductBinding
    private lateinit var product: ProductOnDetailDTO

    private val REQUEST_IMAGE_CAPTURE = 1
    var editPhoto: String? = null
    var isCheked : Boolean = false

    override fun processRenderState(renderState: DetailProductState, context: Context) {
        when(renderState){
            is DetailProductState.CategoriesLoadedOnUpdate ->{
                DialogCategory(categotyList = renderState.categoryOnLoadedList) { selectedCategory ->
                    binding.tvCategoryOnUpdate.text = selectedCategory.categoryName
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is DetailProductState.SupplierLoadedOnUpdate ->{
                DialogSupplier(supplierList = renderState.supplierOnUpdateList){selectedSupplier ->
                    binding.tvSupplierOnUpdate.text = selectedSupplier.supplierName
                }.show(parentFragmentManager, DialogSupplier.TAG)
            }
            is DetailProductState.WarehouseLoadedOnUpdate ->{
                DialogWarehouse(warehouseList = renderState.warehouseOnUpdateList){selectedWarehouse ->
                    binding.tvWarehouseOnUpdate.text = selectedWarehouse.warehouseName
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is DetailProductState.AreaWarehouseLoadedOnUpdate ->{
                DialogAreaWarehouse(areaWarehouseList = renderState.areaWarehouseOnUpdateList){selectedAreaWarehouse ->
                    binding.tvAreaWarehouseOnUpdate.text = selectedAreaWarehouse.areaWarehouseName
                }.show(parentFragmentManager, DialogAreaWarehouse.TAG)
            }
            is DetailProductState.SuccessProductUpdate ->{
                context.showCustomToast(renderState.message, SelectedIcon.SUCCESS)
                binding.sCUpdateProduct.isChecked = false
                isCheked = false
                appState.onDrawinventoryControlFragment?.invoke()
                binding.vfActivateProductEdition.displayedChild = 0

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(Constants.PRODUCT_KEY) as ProductOnDetailDTO
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
//        binding.btnTakePhotoOnUpdate.setOnClickListener {
//            context?.toast("pressed")
//            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
//            } else {
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//                } else {
//                    requireContext().toast("No se encontró una aplicación de cámara en tu dispositivo.")
//                }
//            }
//        }


        binding.btnUpdateProduct.setOnClickListener {
            viewModel.updateProduct(product.idProduct,binding.etProductNameOnUpdate.text.toString().trim(),binding.tvCategoryOnUpdate.text.toString(),binding.etAmountOnUpdate.text.toString().trim().toInt()
            ,binding.tvSupplierOnUpdate.text.toString(),binding.tvWarehouseOnUpdate.text.toString(),binding.tvAreaWarehouseOnUpdate.text.toString(),editPhoto)
        }
        binding.btnNextViewOnUpdate.setOnClickListener {
            binding.vfEditProduct.showNext()
        }

        binding.btnBackViewOnUpdate.setOnClickListener {
            binding.vfEditProduct.showPrevious()
        }

        binding.tvCategoryOnUpdate.setOnClickListener {
            viewModel.requestCategoryListOnUpdate()
        }

        binding.tvSupplierOnUpdate.setOnClickListener {
            viewModel.requestSupplierOnUpdate()
        }

        binding.tvWarehouseOnUpdate.setOnClickListener {
            viewModel.requestWarehouseOnUpdate()
        }

        binding.tvAreaWarehouseOnUpdate.setOnClickListener {
            viewModel.requestAreaWarehouseOnUpdate(binding.tvAreaWarehouseOnUpdate.text.toString().trim())
        }

        binding.sCUpdateProduct.setOnCheckedChangeListener { _, isChecked ->
            context?.logi("[TESTCHECK] -> isChecked before : $isCheked")
            if (isChecked) {
                isCheked = true
                context?.logi("[TESTCHECK] -> isChecked : $isCheked")
                context?.toast("Hola Checked")
                binding.tvSwitchDescription.text ="Desactivar Modificación"
                binding.vfActivateProductEdition.showNext()
                loadProductOnEditData()
            } else {
                isCheked = false
                context?.logi("[TESTCHECK] -> isChecked : $isCheked")
                binding.tvSwitchDescription.text ="Activar Modificación"
                binding.vfActivateProductEdition.showPrevious()
                binding.vfEditProduct.displayedChild = 0
            }
        }

        loadProductDetailsData()
        binding.btnBack.setOnClickListener {
            context?.logi("[TESTCHECK] -> isChecked btnBack: $isCheked")
            if (isCheked){
                context!!.toast("Desactive el modo Edición")
                return@setOnClickListener
            }

            appState.onDrawinventoryControlFragment?.invoke()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmapOnUpdate = data?.extras?.get("data") as Bitmap
            binding.ivEditProduct.setImageBitmap(imageBitmapOnUpdate)
            editPhoto = viewModel.bitmapToBase64(imageBitmapOnUpdate)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } else {
                    requireContext().toast("No se encontró una aplicación de cámara en tu dispositivo.")
                }
            } else {
                requireContext().toast("No se concedió permiso para acceder a la cámara.")
            }
        }
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

    private fun loadProductOnEditData(){
        val bitmap = base64ToBitmap(product.photo)
        binding.ivEditProduct.setImageBitmap(bitmap)
        binding.tvCategoryOnUpdate.text = product.categoryName
        binding.tvSupplierOnUpdate.text = product.supplierName
        binding.tvWarehouseOnUpdate.text = product.warehouse
        binding.tvAreaWarehouseOnUpdate.text = product.areaWarehouse
        binding.etProductNameOnUpdate.setText(product.productName)
        binding.etAmountOnUpdate.setText(product.quantity.toString())
    }
    private fun loadProductDetailsData() {
        binding.tvProductNameTitle.text = product.productName
        binding.tvProductInfoCategory.text = product.categoryName
        binding.tvProductInfoQuantity.text = product.quantity.toString()
        binding.tvProductInfoSupplier.text = product.supplierName
        binding.tvProductInfoWarehouse.text = product.warehouse
        binding.tvProductInfoWarehouseArea.text = product.areaWarehouse
        val bitmap = base64ToBitmap(product.photo)
        binding.ivProductPhotoInfo.setImageBitmap(bitmap)
    }


}