package org.yanfalcao.calculadorademercado

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform