package com.app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.data.local.Converters

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Long,
    val uri: String,
    val url: String,
    @ColumnInfo(name = "asset_id") val assetId: Long,
    val source: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
    val updated: String,
    val section: String,
    val subsection: String,
    @ColumnInfo(name = "nytdsection") val nytdSection: String,
    val adxKeywords: String?,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    @TypeConverters(Converters::class) val media: String,
    @ColumnInfo(name = "eta_id") val etaId: Int,
)
