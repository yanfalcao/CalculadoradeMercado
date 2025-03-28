package com.yanfalcao.data.extensions

import com.yanfalcao.database.model.CartEntity
import com.yanfalcao.model.Cart
import com.yanfalcao.model.Item
import kotlinx.datetime.Instant

fun Cart.toEntity(): CartEntity {
    return CartEntity(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt.epochSeconds
    )
}

fun CartEntity.toModel(items: List<Item>): Cart {
    return Cart(
        id = this.id,
        name = this.name,
        list = items,
        createdAt = Instant.fromEpochSeconds(this.createdAt)
    )
}