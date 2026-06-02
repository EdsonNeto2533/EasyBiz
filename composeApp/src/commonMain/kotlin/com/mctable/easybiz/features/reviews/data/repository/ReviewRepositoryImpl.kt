package com.mctable.easybiz.features.reviews.data.repository

import com.mctable.easybiz.features.reviews.data.datasource.ReviewDatasource
import com.mctable.easybiz.features.reviews.data.mapper.ReviewMapper
import com.mctable.easybiz.features.reviews.data.model.SubmitReviewRequest
import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity
import com.mctable.easybiz.features.reviews.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val datasource: ReviewDatasource
) : ReviewRepository {

    override suspend fun getBusinessReviews(businessId: String): Result<List<ReviewEntity>> =
        runCatching {
            return datasource.getBusinessReviews(businessId).map { list ->
                list.map { ReviewMapper.toEntity(it) }
            }
        }

    override suspend fun submitReview(
        orderId: String,
        rating: Int,
        comment: String?
    ): Result<Unit> = runCatching {
        return datasource.submitReview(
            orderId,
            SubmitReviewRequest(nota = rating, comentario = comment)
        )
    }
}