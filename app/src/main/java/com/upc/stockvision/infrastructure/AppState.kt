package com.upc.stockvision.infrastructure

import com.upc.stockvision.domain.dto.ProductDTO
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppState @Inject constructor(){
    var onDrawProductDetail: ((product : ProductOnDetailDTO) -> Unit) ?= null

    var onDrawinventoryControlFragment: (() -> Unit) ?= null

    var onDrawCreateReserveArea: (() -> Unit) ?= null

    var onDrawCreateMovement: (() -> Unit) ?= null

    var onDrawMovement : (() -> Unit) ?= null

    var onDrawShowReserve: (() -> Unit) ?= null
}