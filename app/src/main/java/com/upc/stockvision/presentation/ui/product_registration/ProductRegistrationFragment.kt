package com.upc.stockvision.presentation.ui.product_registration

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentProductRegistrationBinding
import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.DialogCategory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductRegistrationFragment @Inject constructor() : BaseFragment<ProductRegistrationViewModel,ProductRegistrationState>() {
    val viewModel: ProductRegistrationViewModel by viewModels()
    private lateinit var binding: FragmentProductRegistrationBinding

    override fun processRenderState(renderState: ProductRegistrationState, context: Context) {
        TODO("Not yet implemented")
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
        binding.etCategory.setOnClickListener {
            cargaDatos()
        }

    }

    private fun showView(){
        binding.btnNext.setOnClickListener {
            binding.vfRegisterProduct.showNext()
        }

        binding.btnBack.setOnClickListener {
            binding.vfRegisterProduct.showPrevious()
        }
    }

    private fun cargaDatos(){
// Crea una lista de categorías de ejemplo
        val categories = listOf(
            CategoryDTO(codeCategory = 1, categoryName = "Electronics"),
            CategoryDTO(codeCategory = 2, categoryName = "Books"),
            CategoryDTO(codeCategory = 3, categoryName = "Clothing"),
            CategoryDTO(codeCategory = 4, categoryName = "Home & Garden")
        )

// Crea una instancia de ResponseGenericDTO con la lista de categorías
        val response = ResponseGenericDTO(
            content = categories,
            isValid = true,
            exceptions = emptyList() // O puedes agregar alguna excepción si es necesario
        )

         DialogCategory(response) { selectedCategory ->
            binding.etCategory.text = selectedCategory.categoryName
        }.show(parentFragmentManager, DialogCategory.TAG)
    }

//    private fun showPopupWindow(anchor: View) {
//        // Lista de productos
//        val productos = arrayOf("Producto 1", "Producto 2", "Producto 3", "Producto 4",  "Producto 3", "Producto 4")
//
//        // Crear un ListView
//        val listView = ListView(requireContext())
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, productos)
//        listView.adapter = adapter
//
//        // Crear el PopupWindow
//        val popupWindow = PopupWindow(listView,
//            RelativeLayout.LayoutParams.MATCH_PARENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT)
//
//        // Mostrar el PopupWindow
//        popupWindow.isFocusable = true
//        popupWindow.showAsDropDown(anchor)
//
//        // Manejar el evento de selección
//        listView.setOnItemClickListener { _, _, position, _ ->
//            val selectedProduct = productos[position]
//            binding.etCategory.setText(selectedProduct) // Establecer el texto en el EditText
//            popupWindow.dismiss() // Cerrar el PopupWindow
//        }
//    }


}