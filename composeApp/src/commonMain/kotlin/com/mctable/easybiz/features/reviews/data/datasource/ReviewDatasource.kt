package com.mctable.easybiz.features.reviews.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.reviews.data.mapper.ReviewMapper
import com.mctable.easybiz.features.reviews.data.model.ReviewResponseModel
import com.mctable.easybiz.features.reviews.data.model.SubmitReviewRequest

interface ReviewDatasource {
    suspend fun getBusinessReviews(businessId: String): Result<List<ReviewResponseModel>>
    suspend fun submitReview(orderId: String, request: SubmitReviewRequest): Result<Unit>
}

class ReviewDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : ReviewDatasource {

    override suspend fun getBusinessReviews(businessId: String): Result<List<ReviewResponseModel>> {
        return networking.get(
            host = appEnv.host,
            path = "/avaliacoes/negocio/$businessId",
            responseMapper = { jsonString ->
                ReviewMapper.parseListResponse(jsonString)
            }
        )
    }

    override suspend fun submitReview(orderId: String, request: SubmitReviewRequest): Result<Unit> {
        return networking.post(
            host = appEnv.host,
            path = "/avaliacoes/pedido/$orderId",
            body = request,
            responseMapper = { }
        )
    }
}