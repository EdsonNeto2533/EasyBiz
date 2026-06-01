package com.mctable.easybiz.features.reviews.domain.usecase

import com.mctable.easybiz.features.reviews.domain.repository.ReviewRepository

interface SubmitReviewUseCase {
    suspend fun execute(orderId: String, rating: Int, comment: String?): Result<Unit>
}

class SubmitReviewUseCaseImpl(
    private val repository: ReviewRepository
) : SubmitReviewUseCase {
    override suspend fun execute(orderId: String, rating: Int, comment: String?): Result<Unit> {
        return repository.submitReview(orderId, rating, comment)
    }
}