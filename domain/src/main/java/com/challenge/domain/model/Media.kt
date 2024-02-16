package com.challenge.domain.model

data class Media(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    val approvedForSyndication: Int,
    val mediaMetadata: List<MediaMetadata>,
)
