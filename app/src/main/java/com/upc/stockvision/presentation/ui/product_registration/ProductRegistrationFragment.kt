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
        when(renderState){
            is ProductRegistrationState.CategoriesLoaded ->{
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
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
        binding.tvCategory.setOnClickListener {
            viewModel.requestCategoryList()
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
            binding.tvCategory.text = selectedCategory.categoryName
        }.show(parentFragmentManager, DialogCategory.TAG)
    }

    override fun onLoading(isStarted: Boolean) {
        super.onLoading(isStarted)
        binding.clLoading.visibility = if (isStarted) View.VISIBLE else View.GONE
    }

}