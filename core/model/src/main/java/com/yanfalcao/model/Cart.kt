package com.yanfalcao.model

import java.util.Date

data class Cart(
    val id: String,
    val name: String,
    val list: List<Item>,
    val createdAt: Date
)
