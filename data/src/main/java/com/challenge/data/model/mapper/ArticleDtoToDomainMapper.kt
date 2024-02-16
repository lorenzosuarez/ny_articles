package com.challenge.data.model.mapper

import com.challenge.data.model.dto.ArticleDto
import com.challenge.data.model.dto.MediaDto
import com.challenge.data.model.dto.MediaMetadataDto
import com.challenge.domain.model.Article
import com.challenge.domain.model.Media
import com.challenge.domain.model.MediaMetadata

fun ArticleDto.toArticle(): Article {
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
        byline = this.byline,
        type = this.type,
        title = this.title,
        abstract = this.abstract,
        desFacet = this.desFacet,
        orgFacet = this.orgFacet,
        perFacet = this.perFacet,
        geoFacet = this.geoFacet,
        media = this.media.map(MediaDto::toDomain),
        etaId = this.etaId,
    )
}

fun MediaDto.toDomain(): Media {
    return Media(
        type = this.type,
        subtype = this.subtype,
        caption = this.caption,
        copyright = this.copyright,
        approvedForSyndication = this.approvedForSyndication,
        mediaMetadata = this.mediaMetadata.map(MediaMetadataDto::toDomain),
    )
}

fun MediaMetadataDto.toDomain(): MediaMetadata {
    return MediaMetadata(
        url = this.url,
        format = this.format,
        height = this.height,
        width = this.width,
    )
}
