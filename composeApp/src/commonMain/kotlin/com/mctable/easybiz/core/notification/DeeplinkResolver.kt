package com.mctable.easybiz.core.notification

import com.mctable.easybiz.core.navigation.Destination

class DeeplinkResolver(private val pendingDeeplinkHolder: PendingDeeplinkHolder) {

    fun resolve(data: Map<String, String?>) {
        val destination = buildDestination(data) ?: return
        pendingDeeplinkHolder.set(destination)
    }

    private fun buildDestination(data: Map<String, String?>): Destination? {
        return when (data["type"]) {
            "search_business" -> Destination.SearchBusiness
            "business_details" -> data["id"]?.let { Destination.BusinessDetails(it) }
            "chat" -> data["orderId"]?.let { Destination.Chat(it) }
            "my_orders" -> Destination.MyOrders(
                paper = data["paper"] ?: "CLIENTE",
                businessId = data["businessId"]
            )
            "reviews" -> {
                val orderId = data["orderId"] ?: return null
                val businessId = data["businessId"] ?: return null
                Destination.Reviews(orderId, businessId)
            }
            "profile" -> Destination.Profile
            "my_business" -> Destination.MyBusiness
            "my_favorites" -> Destination.MyFavorites
            else -> null
        }
    }
}