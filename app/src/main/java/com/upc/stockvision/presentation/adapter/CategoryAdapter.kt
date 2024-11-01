package com.upc.stockvision.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.stockvision.databinding.ItemCategoryBinding
import com.upc.stockvision.domain.dto.CategoryDTO


class CategoryAdapter(private val context: Context, private var categotyList : List<CategoryDTO>, val onClick : (category : CategoryDTO) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            with(categotyList[position]){
                binding.tvCategoryCode.text = codeCategory.toString()
                binding.tvCategoryName.text = categoryName
                binding.tvIndex.text = (position + 1).toString()
                val onClick : (view : View) -> Unit = { v : View ->
                    onClick(this)
                }
                binding.root.setOnClickListener(onClick)
                binding.tvCategoryCode.setOnClickListener(onClick)
                binding.tvCategoryName.setOnClickListener(onClick)
            }
        }

    }

    override fun getItemCount(): Int = categotyList.size

    inner class ViewHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}

