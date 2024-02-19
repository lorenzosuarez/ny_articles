package com.app.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    val status: String,
    val copyright: String,
    @SerialName("num_results")
    val numResults: Int,
    val results: List<ArticleDto>,
)
