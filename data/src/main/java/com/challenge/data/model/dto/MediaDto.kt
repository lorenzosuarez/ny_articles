package com.challenge.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDto(
    val type: String,
    val subtype: String,
    val caption: String,
    @SerialName("copyright") val copyright: String,
    @SerialName("approved_for_syndication") val approvedForSyndication: Int,
    @SerialName("media-metadata") val mediaMetadata: List<MediaMetadataDto>,
)
