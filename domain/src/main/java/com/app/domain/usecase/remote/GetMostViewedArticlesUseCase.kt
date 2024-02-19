package com.app.domain.usecase.remote

import com.app.domain.model.Article
import com.app.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetMostViewedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(period: Int): Flow<Result<List<Article>>> = articlesRepository.getMostViewedArticles(period)
}
