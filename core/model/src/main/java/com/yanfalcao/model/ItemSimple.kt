package com.yanfalcao.model

data class ItemSimple(
    override val id: String,
    override val unitPrice: Float,
    override val amount: Int,
    val name: String,
) : Item
