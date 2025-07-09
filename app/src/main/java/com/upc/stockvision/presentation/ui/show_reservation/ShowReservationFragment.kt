package com.upc.stockvision.presentation.ui.show_reservation

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentShowReservationBinding
import com.upc.stockvision.domain.dto.ReservationDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.adapter.ReserveAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ShowReservationFragment @Inject constructor(val appState: AppState) : BaseFragment<ShowReservationViewModel, ShowReservationState>() {

    val viewModel: ShowReservationViewModel by viewModels()
    private lateinit var binding: FragmentShowReservationBinding

    private lateinit var reservationList: List<ReservationDetailDTO>
    private lateinit var reserveAdapter: ReserveAdapter

    val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun processRenderState(renderState: ShowReservationState, context: Context) {
        when(renderState){
            is ShowReservationState.ReserveLoaded ->{
                reservationList = renderState.reserveList
                reserveAdapter.setReserves(reservationList)
            }

            else -> {}
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowReservationBinding.inflate(inflater, container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)

        binding.rvReserve.layoutManager = LinearLayoutManager(requireContext())
        reserveAdapter = ReserveAdapter(requireContext()) {
            appState.onDrawReserveDetail?.invoke(it)
            context?.toast("${it.reservationId} ")
        }

        binding.rvReserve.adapter = reserveAdapter
        initView()
    }

    fun initView(){
        viewModel.requestReserveList()

        binding.btnCalenderStartTime.setOnClickListener {
            mostrarSelectorDeFecha(0)
        }

        binding.btnCalenderEndTime.setOnClickListener {
            mostrarSelectorDeFecha(1)
        }
//        binding.btnCreateReserve.setOnClickListener {
//            appState.onDrawCreateReserveArea?.invoke()
//
//
//        }
        binding.btnSearchReserveByDate.setOnClickListener {
            val startDate = try {
                inputDateFormat.parse(binding.tvStartTimeReserve.text.toString())
            } catch (e: Exception) {
                null
            }

            val endDate = try {
                inputDateFormat.parse(binding.tvEndTimeReserve.text.toString())
            } catch (e: Exception) {
                null
            }
            reserveAdapter.filterByDateRange(startDate,endDate)
        }

        binding.btnResetFilter.setOnClickListener {
            reserveAdapter.resetFilter()
        }
    }

    private fun mostrarSelectorDeFecha(type : Int) {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerTheme,
            { _, año, mesSeleccionado, diaSeleccionado ->
                val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$año"
                if (type == 0) binding.tvStartTimeReserve.text = fechaSeleccionada else binding.tvEndTimeReserve.text = fechaSeleccionada
            },
            year, month, day
        )

        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrincipalDatePicker))
        }
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrincipalDatePicker))
        }
    }

}