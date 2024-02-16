package com.challenge.data.model.mapper

import com.challenge.data.local.entities.ArticleEntity
import com.challenge.domain.model.Article
import com.challenge.domain.model.Media
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json =
    Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

fun ArticleEntity.toArticle(): Article {
    val mediaList: List<Media> = json.decodeFromString(this.media)

    return Article(
        uri = this.uri,
        url = this.url,
        id = this.id,
        assetId = this.assetId,
        source = this.source,
        publishedDate = this.publishedDate,
        updated = this.updated,
        section = this.section,
        subsection = this.subsection,
        nytdSection = this.nytdSection,
        adxKeywords = this.adxKeywords.orEmpty(),
        byline = this.byline,
        type = this.type,
        title = this.title,
        abstract = this.abstract,
        desFacet = this.adxKeywords?.split(", ") ?: emptyList(),
        orgFacet = emptyList(),
        perFacet = emptyList(),
        geoFacet = emptyList(),
        media = mediaList,
        etaId = this.etaId,
    )
}
