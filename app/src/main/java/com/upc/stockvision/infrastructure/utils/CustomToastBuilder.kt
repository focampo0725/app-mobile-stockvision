package com.upc.stockvision.infrastructure.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.upc.stockvision.R
import com.upc.stockvision.databinding.CustomToastBinding

enum class SelectedIcon(val iconResId: Int, val backgroundColor: Int, val textColor: Int) {
    SUCCESS(R.drawable.ic_alert_success_64, R.color.alert_success, R.color.white),
    WARNING(R.drawable.ic_alert_warning_64, R.color.alert_warning, R.color.alert_warning_text_color),
    ERROR(R.drawable.ic_alert_error_64, R.color.alert_error, R.color.alert_error_text_color)
}


class CustomToastBuilder(private val context: Context) {
    private var mensaje: String = ""
    private var icon: Int? = null
    private var selectedIcon: SelectedIcon? = null

    fun setMensaje(mensaje: String) = apply { this.mensaje = mensaje }

    // Establece el icono y guarda el enum
    fun setFoundIcon(selectedIcon: SelectedIcon) = apply {
        this.icon = selectedIcon.iconResId
        this.selectedIcon = selectedIcon
    }

    fun build() {
        val binding = CustomToastBinding.inflate(LayoutInflater.from(context))

        binding.tvMessage.text = mensaje

        // Configura la imagen si se proporciona
        icon?.let {
            binding.ivIcon.setImageResource(it)
            binding.ivIcon.visibility = View.VISIBLE
        } ?: run {
            binding.ivIcon.visibility = View.GONE
        }

        // Establecer el color de fondo según el icono seleccionado
        binding.flBackground.setBackgroundColor(selectedIcon?.backgroundColor ?: R.color.text_animation)

        // Configura el color del texto
        selectedIcon?.let {
            binding.tvMessage.setTextColor(context.getColor(it.textColor))
        }

        // Crea y muestra el Toast
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = binding.root
        toast.show()
    }
}

