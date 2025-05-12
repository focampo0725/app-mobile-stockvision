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
import com.upc.stockvision.databinding.DialogCategoryBinding
import com.upc.stockvision.databinding.DialogProductMovementBinding
import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.domain.dto.TypeMovementDTO
import com.upc.stockvision.presentation.adapter.CategoryAdapter
import com.upc.stockvision.presentation.adapter.TypeProductMovementAdapter

class DialogTypeMovement (val typeProductMovementList : ResponseGenericDTO<TypeMovementDTO>, val onClickTypeProductMovement : (category : TypeMovementDTO) -> Unit): DialogFragment(){
    lateinit var binding : DialogProductMovementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogLight) // Dialog Light
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogProductMovementBinding.inflate(LayoutInflater.from(context))
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val adapter = TypeProductMovementAdapter(context!!, typeProductMovementList.content){
            onClickTypeProductMovement(it)
            dismiss()
        }
        binding.rvTypeProductMovement.layoutManager = LinearLayoutManager(context)
        binding.rvTypeProductMovement.adapter = adapter
        binding.rvTypeProductMovement.setHasFixedSize(true)
//        RecyclerViewUtil.init(context!!, binding.recyclerViewCompanies, adapter)
        return binding.root
    }

    companion object {
        const val TAG = "DialogTypeMovement"
    }
}