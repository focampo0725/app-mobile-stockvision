package com.upc.stockvision.presentation.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.DialogWarehouseBinding
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.domain.dto.WarehouseDTO
import com.upc.stockvision.presentation.adapter.SupplierAdapter
import com.upc.stockvision.presentation.adapter.WarehouseAdapter

class DialogWarehouse(val warehouseList : ResponseGenericDTO<WarehouseDTO>, val onClickCategory : (category : WarehouseDTO) -> Unit):DialogFragment() {
    lateinit var binding : DialogWarehouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogLight)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogWarehouseBinding.inflate(LayoutInflater.from(context))
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val adapter = WarehouseAdapter(context!!, warehouseList.content){
            onClickCategory(it)
            dismiss()
        }
        binding.rvWarehouse.layoutManager = LinearLayoutManager(context)
        binding.rvWarehouse.adapter = adapter
        binding.rvWarehouse.setHasFixedSize(true)
//        RecyclerViewUtil.init(context!!, binding.recyclerViewCompanies, adapter)
        return binding.root
    }

    companion object {
        const val TAG = "DialogWarehouse"
    }
}