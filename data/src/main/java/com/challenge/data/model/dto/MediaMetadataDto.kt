package com.challenge.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MediaMetadataDto(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int,
)
