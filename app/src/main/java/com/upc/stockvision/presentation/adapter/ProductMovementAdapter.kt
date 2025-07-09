package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemMovementBinding
import com.upc.stockvision.domain.dto.ProductMovementDTO
import java.text.SimpleDateFormat
import java.util.*

class ProductMovementAdapter(private val context: Context, val onClick: (productMovement: ProductMovementDTO) -> Unit) : RecyclerView.Adapter<ProductMovementAdapter.ViewHolder>() {


    private var productMovementList: List<ProductMovementDTO> = emptyList()
    private var filteredList: MutableList<ProductMovementDTO> = mutableListOf()

    private val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)


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
        Log.d("AdapterDebug", "StartDate: $startDate - EndDate: $endDate")

        filteredList = productMovementList.filter { reserve ->
            val reserveDate: Date? = try {
                dateFormat.parse(reserve.movementDate.toString())
            } catch (e: Exception) {
                Log.e("AdapterDebug", "Error parsing date: ${reserve.movementDate}")
                null
            }

            if (reserveDate == null) return@filter false

            val afterStart = startDate == null || !reserveDate.before(startDate)
            val beforeEnd = endDate == null || !reserveDate.after(endDate)

            Log.d("AdapterDebug", "Checking: $reserveDate -> $afterStart && $beforeEnd")

            afterStart && beforeEnd
        }.toMutableList()

        Log.d("AdapterDebug", "Filtered items: ${filteredList.size}")

        notifyDataSetChanged()
    }

    fun resetFilter() {
        filteredList = productMovementList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMovementBinding) : RecyclerView.ViewHolder(binding.root)
}