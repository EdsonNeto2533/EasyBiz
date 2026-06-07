package com.mctable.easybiz.core.notification

import com.mctable.easybiz.core.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PendingDeeplinkHolder {
    private val _pending = MutableStateFlow<Destination?>(null)
    val pending: StateFlow<Destination?> = _pending.asStateFlow()

    fun set(destination: Destination) {
        _pending.value = destination
    }

    fun clear() {
        _pending.value = null
    }
}