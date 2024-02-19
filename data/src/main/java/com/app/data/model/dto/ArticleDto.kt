package com.app.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val uri: String,
    val url: String,
    val id: Long,
    @SerialName("asset_id")
    val assetId: Long,
    val source: String,
    @SerialName("published_date")
    val publishedDate: String,
    val updated: String,
    val section: String,
    val subsection: String,
    @SerialName("nytdsection")
    val nytdSection: String,
    @SerialName("adx_keywords")
    val adxKeywords: String,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    @SerialName("des_facet")
    val desFacet: List<String>,
    @SerialName("org_facet")
    val orgFacet: List<String>,
    @SerialName("per_facet")
    val perFacet: List<String>,
    @SerialName("geo_facet")
    val geoFacet: List<String>,
    val media: List<MediaDto>,
    @SerialName("eta_id") val etaId: Int,
)
