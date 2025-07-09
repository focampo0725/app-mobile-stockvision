package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemReserveBinding
import com.upc.stockvision.domain.dto.ReservationDetailDTO
import java.text.SimpleDateFormat
import java.util.*

class ReserveAdapter(
    private val context: Context,
    val onClick: (reserve: ReservationDetailDTO) -> Unit
) : RecyclerView.Adapter<ReserveAdapter.ViewHolder>() {

    private var reserveList: List<ReservationDetailDTO> = emptyList()
    private var filteredList: MutableList<ReservationDetailDTO> = mutableListOf()

//    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

      private val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

    fun setReserves(reserves: List<ReservationDetailDTO>) {
        reserveList = reserves
        filteredList = reserveList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReserveBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val reserve = filteredList[position]
            tvProduct.text = reserve.productName
            tvWarehouse.text = reserve.warehouseName
            tvAreaWarehouse.text = reserve.areaWarehouseName
            root.setOnClickListener { onClick(reserve) }
        }
    }

    fun filterByDateRange(startDate: Date?, endDate: Date?) {
        Log.d("AdapterDebug", "StartDate: $startDate - EndDate: $endDate")

        filteredList = reserveList.filter { reserve ->
            val reserveDate: Date? = try {
                dateFormat.parse(reserve.arrivalDate.toString())
            } catch (e: Exception) {
                Log.e("AdapterDebug", "Error parsing date: ${reserve.arrivalDate}")
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
        filteredList = reserveList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemReserveBinding) : RecyclerView.ViewHolder(binding.root)
}