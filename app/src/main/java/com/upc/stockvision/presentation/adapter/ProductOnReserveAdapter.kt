package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemProductReserveBinding
import com.upc.stockvision.domain.dto.ProductOnDetailDTO


class ProductOnReserveAdapter(private val context: Context, private var productList : List<ProductOnDetailDTO>, val onClick : (productDTO: ProductOnDetailDTO ) -> Unit) :
    RecyclerView.Adapter<ProductOnReserveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductReserveBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(productList[position]){
                binding.tvProductNameReserve.text = productName
                binding.ivProductReserve.setImageBitmap(base64ToBitmap(photo))
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvProductNameReserve.setOnClickListener(onClick)
                binding.ivProductReserve.setOnClickListener(onClick)
            }
        }

    }
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(val binding : ItemProductReserveBinding) : RecyclerView.ViewHolder(binding.root)
}

