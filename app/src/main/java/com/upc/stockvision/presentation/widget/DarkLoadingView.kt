package com.upc.stockvision.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.upc.stockvision.databinding.LayoutLoadingDarkBinding


class DarkLoadingView : ConstraintLayout {

    private var binding : LayoutLoadingDarkBinding

    constructor(context: Context) : super(context) {
        binding = LayoutLoadingDarkBinding.inflate(LayoutInflater.from(context), this, true)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        binding = LayoutLoadingDarkBinding.inflate(LayoutInflater.from(context), this, true)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        binding = LayoutLoadingDarkBinding.inflate(LayoutInflater.from(context), this, true)
    }

}