package com.challenge.data.datasource

import com.challenge.data.local.dao.ArticleDao
import com.challenge.data.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val articleDao: ArticleDao) {
    fun getAllArticles(): Flow<List<ArticleEntity>> = articleDao.getAllArticles()

    suspend fun saveArticles(articles: List<ArticleEntity>) = articleDao.insertAll(articles)
}
