package com.yanfalcao.model.util

sealed class EntityUnit {
    data object Length: EntityUnit()
    data object Mass: EntityUnit()
    data object Volume: EntityUnit()
}