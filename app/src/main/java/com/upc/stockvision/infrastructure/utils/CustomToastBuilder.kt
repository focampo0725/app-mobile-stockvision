package com.upc.stockvision.infrastructure.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.upc.stockvision.R
import com.upc.stockvision.databinding.CustomToastBinding

enum class SelectedIcon(val iconResId: Int, val backgroundColor: Int, val textColor: Int) {
    SUCCESS(R.drawable.ic_alert_success_64, R.color.alert_success, R.color.white),
    WARNING(R.drawable.ic_alert_warning_white_64, R.color.alert_warning, R.color.white),
    ERROR(R.drawable.ic_alert_error_white_64, R.color.alert_error, R.color.white)
}

class CustomToastBuilder(private val context: Context) {
    private var mensaje: String = ""
    private var icon: Int? = null
    private var selectedIcon: SelectedIcon? = null

    // Establece el mensaje
    fun setMensaje(mensaje: String) = apply { this.mensaje = mensaje }

    // Establece el icono y guarda el enum
    fun setFoundIcon(selectedIcon: SelectedIcon) = apply {
        this.icon = selectedIcon.iconResId
        this.selectedIcon = selectedIcon
    }

    fun build() {
        // Inflar el layout personalizado del Toast
        val binding = CustomToastBinding.inflate(LayoutInflater.from(context))

        // Establecer el mensaje en el TextView
        binding.tvMessage.text = mensaje

        // Configura la imagen si se proporciona
        icon?.let {
            binding.ivIcon.setImageResource(it)
            binding.ivIcon.visibility = View.VISIBLE
        } ?: run {
            binding.ivIcon.visibility = View.GONE
        }

        // Establecer el color de fondo del LinearLayout
        val backgroundColor = ContextCompat.getColor(
            context,
            selectedIcon?.backgroundColor ?: R.color.text_animation // Si no se selecciona un icono, usa un color predeterminado
        )
        binding.llToastContainer.setBackgroundColor(backgroundColor)

        // Establecer el color del texto
        selectedIcon?.let {
            binding.tvMessage.setTextColor(context.getColor(it.textColor))
        }

        // Crear y mostrar el Toast
        val toast = Toast(context)
//        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM , 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = binding.root
        toast.show()
    }
}
