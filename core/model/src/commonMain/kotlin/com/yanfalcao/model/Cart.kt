package com.yanfalcao.model

import kotlinx.datetime.Instant

data class Cart(
    val id: String,
    val name: String,
    val list: List<Item>,
    val createdAt: Instant
)
