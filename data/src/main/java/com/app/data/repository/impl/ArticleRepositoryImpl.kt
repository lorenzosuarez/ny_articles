package com.app.data.repository.impl

import com.app.data.datasource.LocalDataSource
import com.app.data.datasource.RemoteDataSource
import com.app.data.local.entities.ArticleEntity
import com.app.data.model.dto.ArticleDto
import com.app.data.model.mapper.toArticle
import com.app.data.model.mapper.toEntity
import com.app.domain.model.Article
import com.app.domain.repository.ArticlesRepository
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
