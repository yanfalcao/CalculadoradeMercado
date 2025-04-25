package com.yanfalcao.designsystem.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object EventManager {
    private val eventChannel = Channel<AppEvent>(Channel.BUFFERED)

    val eventsFlow = eventChannel.receiveAsFlow()

    fun triggerEvent(event: AppEvent) {
        CoroutineScope(Dispatchers.Default).launch { eventChannel.send(event) }
    }

    sealed class AppEvent {
        data class ShowSnackbar(val snackEvent: EnumSnackEvent) : AppEvent()
        data class NavigateToProductDetail(val productId: String? = null) : AppEvent()
        object OpenBottomSheet : AppEvent()
        object CloseBottomSheet : AppEvent()
        object CloseScreen : AppEvent()
    }
}