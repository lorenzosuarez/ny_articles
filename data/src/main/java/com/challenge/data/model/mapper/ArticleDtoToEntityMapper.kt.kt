package com.challenge.data.model.mapper

import com.challenge.data.local.entities.ArticleEntity
import com.challenge.data.model.dto.ArticleDto
import com.challenge.data.model.dto.MediaDto
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

fun ArticleDto.toEntity(): ArticleEntity {
    val mediaJson = Json.encodeToString(ListSerializer(MediaDto.serializer()), this.media)

    return ArticleEntity(
        id = this.id,
        uri = this.uri,
        url = this.url,
        assetId = this.assetId,
        source = this.source,
        publishedDate = this.publishedDate,
        updated = this.updated,
        section = this.section,
        subsection = this.subsection,
        nytdSection = this.nytdSection,
        adxKeywords = this.desFacet.joinToString(separator = ", "),
        byline = this.byline,
        type = this.type,
        title = this.title,
        abstract = this.abstract,
        media = mediaJson,
        etaId = this.etaId,
    )
}
