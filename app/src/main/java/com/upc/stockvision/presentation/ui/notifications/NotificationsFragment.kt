package com.upc.stockvision.presentation.ui.notifications

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentNotificationsBinding
import com.upc.stockvision.domain.dto.NotificationDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment @Inject constructor(val appState: AppState) : BaseFragment<NotificationsViewModel, NotificationsState>() {
    val viewModel: NotificationsViewModel by viewModels()
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notificationList: List<NotificationDTO>
    private lateinit var notificationAdapter: NotificationAdapter

    override fun processRenderState(renderState: NotificationsState, context: Context) {
        when(renderState){
            is NotificationsState.NotificationLoaded ->{
                notificationList = renderState.notificationList
                notificationAdapter.setNotification(notificationList)
            }
         else->{

         }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        notificationAdapter = NotificationAdapter(requireContext()) {
            context?.toast("${it.typeNotification} ")
        }
        binding.rvNotifications.adapter = notificationAdapter
        viewModel.requestReserveList()
    }


}