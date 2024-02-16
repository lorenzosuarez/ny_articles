package com.challenge.domain.repository

import com.challenge.domain.model.Article

interface ArticlesRepository {
    suspend fun getMostEmailedArticles(period: Int): List<Article>
    suspend fun getMostSharedArticles(period: Int, shareType: String): List<Article>
    suspend fun getMostViewedArticles(period: Int): List<Article>
}