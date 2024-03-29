package com.app.data.datasource

import com.app.data.model.dto.ArticlesResponse
import com.app.data.network.api.ApiService

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getMostEmailedArticles(period: Int): ArticlesResponse = apiService.getMostEmailedArticles(period)

    suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): ArticlesResponse = apiService.getMostSharedArticles(period, shareType)

    suspend fun getMostViewedArticles(period: Int): ArticlesResponse = apiService.getMostViewedArticles(period)
}
