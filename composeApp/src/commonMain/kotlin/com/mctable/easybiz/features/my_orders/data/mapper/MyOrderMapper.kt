package com.mctable.easybiz.features.my_orders.data.mapper

import com.mctable.easybiz.features.my_orders.data.model.MyOrderPageResponseModel
import com.mctable.easybiz.features.my_orders.data.model.MyOrderResponseModel
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderEntity
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus
import kotlinx.serialization.json.Json

object MyOrderMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parsePageResponse(jsonString: String): MyOrderPageResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: MyOrderResponseModel): MyOrderEntity {
        return MyOrderEntity(
            id = model.id,
            clientId = model.clientId,
            clientName = model.clientName,
            businessId = model.businessId,
            businessName = model.businessName,
            description = model.description,
            desiredDate = model.desiredDate,
            status = OrderStatus.fromString(model.status),
            createdAt = model.createdAt,
            businessLogoUrl = model.businessLogoUrl
        )
    }

    fun toPageEntity(model: MyOrderPageResponseModel): MyOrderPageEntity {
        return MyOrderPageEntity(
            content = model.content.map { toEntity(it) },
            totalElements = model.totalElements,
            totalPages = model.totalPages,
            isLast = model.isLast
        )
    }
}
