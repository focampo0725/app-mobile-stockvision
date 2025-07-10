package com.upc.stockvision.presentation.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.DialogConfirmationBinding
import com.upc.stockvision.databinding.DialogWarehouseBinding
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.domain.dto.WarehouseDTO
import com.upc.stockvision.presentation.adapter.SupplierAdapter
import com.upc.stockvision.presentation.adapter.WarehouseAdapter

interface OnDialogListener {
    fun onAceptar()
}
class DialogConfirmation(val description : String, private val onDialogListener: OnDialogListener):DialogFragment() {
    lateinit var binding : DialogConfirmationBinding

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
        binding = DialogConfirmationBinding.inflate(LayoutInflater.from(context))
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false
        binding.tvQuestion.text = description
        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            onDialogListener.onAceptar()
            dismiss()

        }

        return binding.root
    }

    companion object {
        const val TAG = "DialogConfirmation"
    }
}