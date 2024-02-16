package com.challenge.domain.repository

import com.challenge.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun getMostEmailedArticles(period: Int): Flow<Result<List<Article>>>

    suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): Flow<Result<List<Article>>>

    suspend fun getMostViewedArticles(period: Int): Flow<Result<List<Article>>>

    suspend fun getArticleById(articleId: Long): Flow<Result<Article>>
}
