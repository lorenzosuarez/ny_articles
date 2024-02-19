package com.app.data.network.api

import com.app.data.model.dto.ArticlesResponse

interface ApiService {
    suspend fun getMostEmailedArticles(period: Int): ArticlesResponse

    suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): ArticlesResponse

    suspend fun getMostViewedArticles(period: Int): ArticlesResponse
}
