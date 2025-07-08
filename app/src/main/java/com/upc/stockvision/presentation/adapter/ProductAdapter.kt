package com.upc.stockvision.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemProductBinding
import com.upc.stockvision.domain.dto.ProductDTO
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.infrastructure.extensions.toast

class ProductAdapter(private val context: Context , val onClick : (productDetail : ProductOnDetailDTO) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList: List<ProductOnDetailDTO> = emptyList()
    private var filteredList: MutableList<ProductOnDetailDTO> = mutableListOf()

    fun setProduct(product: List<ProductOnDetailDTO>) {
        productList = product
        filteredList = productList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(filteredList[position]) {
                binding.ivProduct.setImageBitmap(base64ToBitmap(photo))
                binding.tvProduct.text = productName
                binding.tvWarehouse.text = warehouse
                binding.tvQuantity.text = quantity.toString()

                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
            }
        }
    }

    // Función para filtrar los productos según los parámetros no nulos
    fun filterProducts(warehouse: String?, category: String?, productName: String?) {
        filteredList = productList.filter { product ->
            // Comprobamos cada campo de forma independiente y lo filtramos solo si no es nulo
            val matchesWarehouse = warehouse?.let { product.warehouseCode == it } ?: true
            val matchesCategory = category?.let { product.categoryName == it } ?: true // Ajusta el nombre del campo si es necesario
            val matchesName = productName?.let { product.productName.contains(it, ignoreCase = true) } ?: true

            // Si todos los filtros aplican (es decir, no son nulos y coinciden), el producto es válido
            matchesWarehouse && matchesCategory && matchesName
        }.toMutableList()

        // Actualizamos el RecyclerView con la lista filtrada
        notifyDataSetChanged()
    }

    // Función para resetear los filtros y mostrar todos los productos
    fun resetFilter() {
        filteredList = productList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}
