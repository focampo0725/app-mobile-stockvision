package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemMovementBinding
import com.upc.stockvision.databinding.ItemReserveBinding
import com.upc.stockvision.domain.dto.ProductMovementDTO
import com.upc.stockvision.domain.dto.ReserveAreaDTO
import java.text.SimpleDateFormat
import java.util.*

class ProductMovementAdapter(
    private val context: Context,
    val onClick: (productMovement: ProductMovementDTO) -> Unit
) : RecyclerView.Adapter<ProductMovementAdapter.ViewHolder>() {

    private var productMovementList: List<ProductMovementDTO> = emptyList()
    private var filteredList: MutableList<ProductMovementDTO> = mutableListOf()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun setReserves(productMovement: List<ProductMovementDTO>) {
        productMovementList = productMovement
        filteredList = productMovementList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovementBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val productMovement = filteredList[position]
            tvProductMovement.text = productMovement.productName
            tvInitialWarehouse.text = productMovement.initialWarehouse
            tvFinalWarehouse.text = productMovement.finalWarehouse
            root.setOnClickListener { onClick(productMovement) }
        }
    }

    fun filterByDateRange(startDate: Date?, endDate: Date?) {
        filteredList = productMovementList.filter { productMovement ->
            val reserveDate: Date? = try {
                dateFormat.parse(productMovement.movementDate)
            } catch (e: Exception) {
                null
            }

            val afterStart = startDate?.let { reserveDate?.compareTo(it) ?: 1 >= 0 } ?: true
            val beforeEnd = endDate?.let { reserveDate?.compareTo(it) ?: -1 <= 0 } ?: true

            afterStart && beforeEnd
        }.toMutableList()
        notifyDataSetChanged()
    }

    fun resetFilter() {
        filteredList = productMovementList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMovementBinding) : RecyclerView.ViewHolder(binding.root)
}