package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemCategoryBinding
import com.upc.stockvision.databinding.ItemSupplierBinding
import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.SupplierDTO


class SupplierAdapter(private val context: Context, private var supplierList : List<SupplierDTO>, val onClick : (category : SupplierDTO) -> Unit) :
    RecyclerView.Adapter<SupplierAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSupplierBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(supplierList[position]){
                binding.tvSupplierCode.text = codeSupplier.toString()
                binding.tvSupplierName.text = supplierName
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvSupplierCode.setOnClickListener(onClick)
                binding.tvSupplierName.setOnClickListener(onClick)
            }
        }

    }

    override fun getItemCount(): Int = supplierList.size

    inner class ViewHolder(val binding : ItemSupplierBinding) : RecyclerView.ViewHolder(binding.root)
}

