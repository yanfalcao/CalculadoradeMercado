package com.yanfalcao.product.di

import com.yanfalcao.product.ProductViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val productModule = module {
    viewModelOf(::ProductViewModel)
}