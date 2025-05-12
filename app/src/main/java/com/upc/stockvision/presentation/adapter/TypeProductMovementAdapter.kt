package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemCategoryBinding
import com.upc.stockvision.databinding.ItemProductMovementBinding
import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.ProductMovementDTO
import com.upc.stockvision.domain.dto.TypeMovementDTO


class TypeProductMovementAdapter(private val context: Context, private var typeProductMovementList : List<TypeMovementDTO>, val onClick : (category : TypeMovementDTO) -> Unit) :
    RecyclerView.Adapter<TypeProductMovementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductMovementBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(typeProductMovementList[position]){
                binding.tvTypeProductMovementCode.text = codeTypeMovement.toString()
                binding.tvTypeProductMovementName.text = typeMovementName
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvTypeProductMovementCode.setOnClickListener(onClick)
                binding.tvTypeProductMovementName.setOnClickListener(onClick)
            }
        }

    }

    override fun getItemCount(): Int = typeProductMovementList.size

    inner class ViewHolder(val binding : ItemProductMovementBinding) : RecyclerView.ViewHolder(binding.root)
}

