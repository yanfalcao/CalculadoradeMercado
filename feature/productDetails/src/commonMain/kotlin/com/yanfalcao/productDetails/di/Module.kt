package com.yanfalcao.productDetails.di

import com.yanfalcao.data.repository.ItemRepository
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.productDetails.ProductDetailsVM
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel { (productId: String?) ->
        ProductDetailsVM(
            productId = productId,
            get<ProductRepository>(),
            get<ItemRepository>()
        )
    }
}