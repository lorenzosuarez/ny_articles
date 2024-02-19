package com.app.data.datasource

import com.app.data.local.dao.ArticleDao
import com.app.data.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val articleDao: ArticleDao) {
    fun getAllArticles(): Flow<List<ArticleEntity>> = articleDao.getAllArticles()

    fun getArticleById(articleId: Long): Flow<ArticleEntity> = articleDao.getArticleById(articleId = articleId)

    suspend fun saveArticles(articles: List<ArticleEntity>) = articleDao.insertAll(articles)
}
