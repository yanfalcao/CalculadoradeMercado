package com.yanfalcao.model

data class ItemSimple(
    override val id: String,
    override val totalPrice: Float,
    override val amount: Int,
    val name: String,
) : Item()
