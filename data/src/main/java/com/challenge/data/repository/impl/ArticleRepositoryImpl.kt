package com.challenge.data.repository.impl

import com.challenge.data.datasource.LocalDataSource
import com.challenge.data.datasource.RemoteDataSource
import com.challenge.data.local.entities.ArticleEntity
import com.challenge.data.model.dto.ArticleDto
import com.challenge.data.model.mapper.toArticle
import com.challenge.data.model.mapper.toEntity
import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ArticlesRepository {
    override suspend fun getArticleById(articleId: Long): Flow<Result<Article>> =
        localDataSource.getArticleById(articleId)
            .map { Result.success(it.toArticle()) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): Flow<Result<List<Article>>> = fetchArticlesFlow { remoteDataSource.getMostSharedArticles(period, shareType).results }

    override suspend fun getMostViewedArticles(period: Int): Flow<Result<List<Article>>> =
        fetchArticlesFlow { remoteDataSource.getMostViewedArticles(period).results }

    override suspend fun getMostEmailedArticles(period: Int): Flow<Result<List<Article>>> =
        fetchArticlesFlow { remoteDataSource.getMostEmailedArticles(period).results }

    private fun fetchArticlesFlow(fetch: suspend () -> List<ArticleDto>): Flow<Result<List<Article>>> =
        flow {
            try {
                val articles = fetchAndSaveArticles(fetch)
                emit(Result.success(articles))
            } catch (e: Exception) {
                emit(handleError(e))
            }
        }

    private suspend fun fetchAndSaveArticles(fetch: suspend () -> List<ArticleDto>): List<Article> {
        val articles = fetch()
        localDataSource.saveArticles(articles.map(ArticleDto::toEntity))
        return articles.map(ArticleDto::toArticle)
    }

    private suspend fun handleError(e: Exception): Result<List<Article>> {
        val localArticles = localDataSource.getAllArticles().firstOrNull()?.map(ArticleEntity::toArticle)
        return if (localArticles.isNullOrEmpty()) {
            Result.failure(e)
        } else {
            Result.success(localArticles)
        }
    }
}
