package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemAreawarehouseBinding
import com.upc.stockvision.databinding.ItemWarehouseBinding
import com.upc.stockvision.domain.dto.AreaWarehouseDTO
import com.upc.stockvision.domain.dto.WarehouseDTO

class AreaWarehouseAdapter(private val context: Context, private var areaWarehouseList : List<AreaWarehouseDTO>, val onClick : (areaWarehouse : AreaWarehouseDTO) -> Unit) :
    RecyclerView.Adapter<AreaWarehouseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAreawarehouseBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(areaWarehouseList[position]){
                binding.tvAreaWarehouseCode.text = codeAreaWarehouse.toString()
                binding.tvAreaWarehouseName.text = areaWarehouseName
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvAreaWarehouseCode.setOnClickListener(onClick)
                binding.tvAreaWarehouseName.setOnClickListener(onClick)
            }
        }

    }

    override fun getItemCount(): Int = areaWarehouseList.size

    inner class ViewHolder(val binding : ItemAreawarehouseBinding) : RecyclerView.ViewHolder(binding.root)
}

