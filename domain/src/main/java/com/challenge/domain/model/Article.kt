package com.challenge.domain.model

data class Article(
    val uri: String,
    val url: String,
    val id: Long,
    val assetId: Long,
    val source: String,
    val publishedDate: String,
    val updated: String,
    val section: String,
    val subsection: String,
    val nytdSection: String,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    val desFacet: List<String>,
    val orgFacet: List<String>,
    val perFacet: List<String>,
    val geoFacet: List<String>,
    val media: List<Media>,
    val etaId: Int,
)
