package com.challenge.data.network.api

import com.challenge.data.model.dto.ArticlesResponse

interface ApiService {
    suspend fun getMostEmailedArticles(period: Int): ArticlesResponse

    suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): ArticlesResponse

    suspend fun getMostViewedArticles(period: Int): ArticlesResponse
}
