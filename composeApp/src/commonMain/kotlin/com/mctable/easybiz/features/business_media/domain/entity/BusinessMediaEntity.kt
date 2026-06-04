package com.mctable.easybiz.features.business_media.domain.entity

data class BusinessMediaEntity(
    val id: String,
    val url: String,
    val type: String,
    val createdAt: String?
) {
    val isVideo: Boolean get() = type.uppercase() == "VIDEO"
}