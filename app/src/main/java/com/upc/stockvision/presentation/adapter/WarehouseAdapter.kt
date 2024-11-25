package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemWarehouseBinding
import com.upc.stockvision.domain.dto.WarehouseDTO


class WarehouseAdapter(private val context: Context, private var warehouseList : List<WarehouseDTO>, val onClick : (category : WarehouseDTO) -> Unit) :
    RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWarehouseBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(warehouseList[position]){
                binding.tvWarehouseCode.text = codeWarehouse.toString()
                binding.tvWarehouseName.text = warehouseName
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvWarehouseCode.setOnClickListener(onClick)
                binding.tvWarehouseName.setOnClickListener(onClick)
            }
        }

    }

    override fun getItemCount(): Int = warehouseList.size

    inner class ViewHolder(val binding : ItemWarehouseBinding) : RecyclerView.ViewHolder(binding.root)
}

