package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.R
import com.upc.stockvision.databinding.ItemNotificationsBinding
import com.upc.stockvision.domain.dto.NotificationDTO


class NotificationAdapter(private val context: Context, val onClick : (category : NotificationDTO) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notificationList : List<NotificationDTO> = emptyList()

    fun setNotification(notifications: List<NotificationDTO>) {
        notificationList = notifications
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNotificationsBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(notificationList[position]){
                binding.tvTitle.text = when (typeNotification) {
                    0 -> "Ingreso de Producto"
                    1 -> "Reserva de Zona"
                    2 -> "Agotamiento de Stock"
                    else -> "Notificación"
                }
                binding.tvProduct.text = productName
                binding.tvWarehouse.text = warehouseName
                binding.tvAreaWarehouse.text = warehouseArea
                binding.tvQuantity.text = quantity.toString()
                val iconRes = when (typeNotification) {
                    0 -> R.drawable.ic_incoming_product_64
                    1 -> R.drawable.ic_reserve_area_64
                    2 -> R.drawable.ic_low_stock_product_64
                    else -> R.drawable.ic_warehouse_64
                }
                binding.ivProduct.setImageResource(iconRes)
                binding.tvDate.text = date
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)

            }
        }


    }

    override fun getItemCount(): Int = notificationList.size

    inner class ViewHolder(val binding : ItemNotificationsBinding) : RecyclerView.ViewHolder(binding.root)
}

