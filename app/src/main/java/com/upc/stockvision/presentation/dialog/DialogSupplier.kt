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
import com.upc.stockvision.databinding.DialogSupplierBinding
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.domain.dto.SupplierDTO
import com.upc.stockvision.presentation.adapter.SupplierAdapter

class DialogSupplier(val supplierList : ResponseGenericDTO<SupplierDTO>, val onClickCategory : (category : SupplierDTO) -> Unit):DialogFragment() {
    lateinit var binding : DialogSupplierBinding

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
        binding = DialogSupplierBinding.inflate(LayoutInflater.from(context))
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val adapter = SupplierAdapter(context!!, supplierList.content){
            onClickCategory(it)
            dismiss()
        }
        binding.rvSupplier.layoutManager = LinearLayoutManager(context)
        binding.rvSupplier.adapter = adapter
        binding.rvSupplier.setHasFixedSize(true)
        return binding.root
    }

    companion object {
        const val TAG = "DialogSupplier"
    }
}