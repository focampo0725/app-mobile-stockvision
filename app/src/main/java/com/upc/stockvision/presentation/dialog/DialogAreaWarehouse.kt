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
import com.upc.stockvision.databinding.DialogAreawarehouseBinding
import com.upc.stockvision.domain.dto.AreaWarehouseDTO
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.presentation.adapter.AreaWarehouseAdapter

class DialogAreaWarehouse(val areaWarehouseList : ResponseGenericDTO<AreaWarehouseDTO>, val onClickCategory : (areaWarhouse : AreaWarehouseDTO) -> Unit):DialogFragment() {
    lateinit var binding : DialogAreawarehouseBinding

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
        binding = DialogAreawarehouseBinding.inflate(LayoutInflater.from(context))
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val adapter = AreaWarehouseAdapter(context!!, areaWarehouseList.content){
            onClickCategory(it)
            dismiss()
        }
        binding.rvAreaWarehouse.layoutManager = LinearLayoutManager(context)
        binding.rvAreaWarehouse.adapter = adapter
        binding.rvAreaWarehouse.setHasFixedSize(true)
        return binding.root
    }

    companion object {
        const val TAG = "DialogAreaWarehouse"
    }
}