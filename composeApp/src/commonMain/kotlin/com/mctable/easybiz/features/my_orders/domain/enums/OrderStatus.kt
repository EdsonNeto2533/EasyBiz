package com.mctable.easybiz.features.my_orders.domain.enums

enum class OrderStatus {
    ABERTO,
    ACEITO,
    CONCLUIDO,
    CANCELADO,
    RECUSADO;

    companion object {
        fun fromString(value: String): OrderStatus {
            return OrderStatus.entries.first {
                it.name.equals(value, ignoreCase = true)
            }
        }
    }
}