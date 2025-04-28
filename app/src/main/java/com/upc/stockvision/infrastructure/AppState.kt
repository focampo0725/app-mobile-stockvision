package com.upc.stockvision.infrastructure

import com.upc.stockvision.domain.dto.ProductDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppState @Inject constructor(){
    var onDrawProductDetail: ((product : ProductDTO) -> Unit) ?= null

    var onDrawInventoryCOntrol: (() -> Unit) ?= null
}