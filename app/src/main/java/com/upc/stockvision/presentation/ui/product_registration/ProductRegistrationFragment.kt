package com.upc.stockvision.presentation.ui.product_registration

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentProductRegistrationBinding
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.DialogAreaWarehouse
import com.upc.stockvision.presentation.dialog.DialogCategory
import com.upc.stockvision.presentation.dialog.DialogSupplier
import com.upc.stockvision.presentation.dialog.DialogWarehouse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductRegistrationFragment @Inject constructor() : BaseFragment<ProductRegistrationViewModel,ProductRegistrationState>() {
    val viewModel: ProductRegistrationViewModel by viewModels()
    private lateinit var binding: FragmentProductRegistrationBinding

    private val REQUEST_IMAGE_CAPTURE = 1
    var photo: String? = null
    var categoryCode: String? = null
    var warehouseCode: String?= null
    var areaWarehouseCode: String? = null
    var supplierCode : String?= null

    override fun processRenderState(renderState: ProductRegistrationState, context: Context) {
        when(renderState){
            is ProductRegistrationState.CategoriesLoaded ->{
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                    categoryCode = selectedCategory.codeCategory
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is ProductRegistrationState.ProductsLoaded ->{
                DialogSupplier(supplierList = renderState.productList){selectedSupplier ->
                    binding.tvSupplier.text = selectedSupplier.supplierName
                    supplierCode = selectedSupplier.codeSupplier
                }.show(parentFragmentManager, DialogSupplier.TAG)
            }
            is ProductRegistrationState.WarehouseLoaded ->{
                DialogWarehouse(warehouseList = renderState.warehouseList){selectedWarehouse ->
                    binding.tvWarehouse.text = selectedWarehouse.warehouseName
                    warehouseCode = selectedWarehouse.codeWarehouse
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is ProductRegistrationState.AreaWarehouseLoaded ->{
                DialogAreaWarehouse(areaWarehouseList = renderState.areaWarehouseList){selectedAreaWarehouse ->
                    binding.tvAreaWarehouse.text = selectedAreaWarehouse.areaWarehouseName
                    areaWarehouseCode = selectedAreaWarehouse.codeAreaWarehouse

                }.show(parentFragmentManager, DialogAreaWarehouse.TAG)
            }
            is ProductRegistrationState.SuccessProductRegister ->{
                context.showCustomToast(renderState.message,SelectedIcon.SUCCESS)
                clearBoxes()
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("ProductRegistrationFragment", "onCreateView called")
        binding = FragmentProductRegistrationBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        showView()
        onInit()


    }
    private fun onInit(){
        binding.tvCategory.setOnClickListener {
            viewModel.requestCategoryList()
        }

        binding.tvSupplier.setOnClickListener {
            viewModel.requestSupplier()
        }

        binding.tvWarehouse.setOnClickListener {
            if (categoryCode!= null){
                viewModel.requestWarehouse(categoryCode!!)
            }else{
                context?.toast("Debe seleccionar una categoría")
            }

        }

        binding.tvAreaWarehouse.setOnClickListener {
            if (warehouseCode != null && categoryCode != null){
                viewModel.requestAreaWarehouse(warehouseCode!!,categoryCode!!)
            }else{
                context?.toast("Debe seleccionar un Almacén")
            }
        }

        binding.btnRegisterProduct.setOnClickListener {
            val productName = binding.etProductName.text.toString().trim()
            val categoryName = binding.tvCategory.text.toString().trim()
            val quantity = binding.etAmount.text.toString().trim().toInt()
            val supplier = binding.tvSupplier.text.toString().trim()
            val warehouse = binding.tvWarehouse.text.toString().trim()
            val areaWarehouse = binding.tvAreaWarehouse.text.toString().trim()

            if (productName.isEmpty() ||
                categoryName.isEmpty() ||
                supplier.isEmpty() ||
                warehouse.isEmpty() ||
                areaWarehouse.isEmpty() || photo == null) {
                context?.toast("Completar los campos")
                return@setOnClickListener
            }

            viewModel.registerProduct(productName, categoryCode!!, quantity, supplierCode!!, areaWarehouseCode!!, photo!!)
        }


        binding.btnTakePhoto.setOnClickListener {
            context?.toast("pressed")
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } else {
                    requireContext().toast("No se encontró una aplicación de cámara en tu dispositivo.")
                }
            }
        }
    }
    private fun showView(){
        binding.btnNext.setOnClickListener {
            if (binding.etProductName.text.toString().isEmpty() || binding.tvCategory.text.isEmpty() || binding.etAmount.text.toString().isEmpty()){
                context?.toast("Debe llenar los datos")
            }else{
                binding.vfRegisterProduct.showNext()
            }
        }

        binding.btnBack.setOnClickListener {
            binding.vfRegisterProduct.showPrevious()
        }
    }
    fun clearBoxes(){
        binding.tvCategory.text = ""
        binding.tvSupplier.text = ""
        binding.tvWarehouse.text = ""
        binding.tvAreaWarehouse.text = ""
        binding.etProductName.setText("")
        binding.etAmount.setText("")

        binding.tvCategory.hint = "Selecciona una categoría"
        binding.tvSupplier.hint = "Selecciona un proveedor"
        binding.tvWarehouse.hint = "Selecciona una ubicación"
        binding.tvAreaWarehouse.hint = "Selecciona una zona"
        binding.etProductName.setHint("Producto")
        binding.tvAreaWarehouse.setHint("Selecciona una zona")
        binding.ivProduct.setImageResource(R.drawable.box_referential)

        binding.vfRegisterProduct.showPrevious()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmapOnUpdate = data?.extras?.get("data") as Bitmap
            binding.ivProduct.setImageBitmap(imageBitmapOnUpdate)
            photo = viewModel.bitmapToBase64(imageBitmapOnUpdate)
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
    override fun onLoading(isStarted: Boolean) {
        super.onLoading(isStarted)
        binding.clLoading.visibility = if (isStarted) View.VISIBLE else View.GONE
    }

}