package com.upc.stockvision.presentation.ui.product_registration

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.applySchedulers
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.subscribeApp
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.sign_in.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class ProductRegistrationState{
    class CategoriesLoaded(val categoriesList : ResponseGenericDTO<CategoryDTO>) : ProductRegistrationState()

}

@HiltViewModel
class ProductRegistrationViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<ProductRegistrationState>, ProductRegistrationState>(),
    IViewModel<ProductRegistrationState> {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context : Context

    fun requestCategoryList(){
        super.sendValue(LCEState.loading(true))
        stockVisionRepository.managementApi.getCategoryList().applySchedulers().subscribeApp(
            onSuccess = {
                super.sendValue(LCEState.loading(false))
                renderState.value = LCEState.Content(ProductRegistrationState.CategoriesLoaded(it))
            }, onError = {
                super.sendValue(LCEState.loading(false))
                context.showCustomToast("Error captred : $it",SelectedIcon.ERROR)

            }
        )
//        xtuRepository.companyListApi.requestCompanyList().applySchedulers().subscribeApp(
//            onSuccess = {
//                context.logi("Emrpesas -> ${it.content}")
//                super.postMessageWithDelay(LCEState.loading(false), 100)
//                super.sendValue(UnitConfigurationState.CompaniesLoaded(it))
//            }, onError = {
//                super.postMessageWithDelay(LCEState.loading(false), 100)
//                context.toast(" $it $ERROR_RETROFIT_LOAD_COMPANIES")
//            })
    }

    override val renderState: MutableLiveData<LCEState<ProductRegistrationState>>
        get() = getLiveData()
}