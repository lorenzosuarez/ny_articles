package com.challenge.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    val approvedForSyndication: Int = 0,
    @SerialName("media-metadata") val mediaMetadata: List<MediaMetadata>,
)
