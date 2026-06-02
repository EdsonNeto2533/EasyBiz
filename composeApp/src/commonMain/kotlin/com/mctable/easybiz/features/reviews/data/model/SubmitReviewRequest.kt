package com.mctable.easybiz.features.reviews.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SubmitReviewRequest(
    val nota: Int,
    val comentario: String? = null
)