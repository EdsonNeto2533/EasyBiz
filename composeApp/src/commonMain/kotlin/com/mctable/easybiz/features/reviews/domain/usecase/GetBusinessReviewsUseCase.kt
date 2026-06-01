package com.mctable.easybiz.features.reviews.domain.usecase

import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity
import com.mctable.easybiz.features.reviews.domain.repository.ReviewRepository

interface GetBusinessReviewsUseCase {
    suspend fun execute(businessId: String): Result<List<ReviewEntity>>
}

class GetBusinessReviewsUseCaseImpl(
    private val repository: ReviewRepository
) : GetBusinessReviewsUseCase {
    override suspend fun execute(businessId: String): Result<List<ReviewEntity>> {
        return repository.getBusinessReviews(businessId)
    }
}