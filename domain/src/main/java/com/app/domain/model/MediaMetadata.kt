package com.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaMetadata(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int,
)
