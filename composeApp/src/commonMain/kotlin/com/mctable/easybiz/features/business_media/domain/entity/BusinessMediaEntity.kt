package com.mctable.easybiz.features.business_media.domain.entity

data class BusinessMediaEntity(
    val id: String,
    val url: String,
    val tipo: String,
    val criadoEm: String?
) {
    val isVideo: Boolean get() = tipo.uppercase() == "VIDEO"
}