package com.yanfalcao.data.di

import com.yanfalcao.data.repository.CartRepository
import com.yanfalcao.data.repository.DefaultCartRepository
import com.yanfalcao.data.repository.DefaultProductRepository
import com.yanfalcao.data.repository.ProductRepository
import org.koin.dsl.module

val dataModule = module {
    single<ProductRepository> {
        DefaultProductRepository(get(), get())
    }
    single<CartRepository> {
        DefaultCartRepository()
    }
}