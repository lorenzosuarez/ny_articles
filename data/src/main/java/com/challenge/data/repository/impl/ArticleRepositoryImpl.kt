package com.challenge.data.repository.impl

import com.challenge.data.datasource.LocalDataSource
import com.challenge.data.datasource.RemoteDataSource
import com.challenge.data.local.entities.ArticleEntity
import com.challenge.data.model.dto.ArticleDto
import com.challenge.data.model.mapper.toArticle
import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import toEntity

class ArticleRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ArticlesRepository {
    override suspend fun getMostEmailedArticles(period: Int): List<Article> =
        runBlocking {
            tryFetchAndSaveArticles { remoteDataSource.getMostEmailedArticles(period).results }
        }

    override suspend fun getMostSharedArticles(
        period: Int,
        shareType: String,
    ): List<Article> =
        runBlocking {
            tryFetchAndSaveArticles {
                remoteDataSource.getMostSharedArticles(
                    period,
                    shareType,
                ).results
            }
        }

    override suspend fun getMostViewedArticles(period: Int): List<Article> =
        runBlocking {
            tryFetchAndSaveArticles { remoteDataSource.getMostViewedArticles(period).results }
        }

    private suspend fun tryFetchAndSaveArticles(fetchArticles: suspend () -> List<ArticleDto>): List<Article> {
        val localArticles = localDataSource.getAllArticles().first().map(ArticleEntity::toArticle)

        if (localArticles.isEmpty()) {
            val remoteArticles = fetchArticles()
            val articlesToSave = remoteArticles.map(ArticleDto::toEntity)
            localDataSource.saveArticles(articlesToSave)
            return articlesToSave.map(ArticleEntity::toArticle)
        }

        return localArticles
    }
}
