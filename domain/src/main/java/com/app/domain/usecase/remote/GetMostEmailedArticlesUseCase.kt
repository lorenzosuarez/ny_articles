package com.app.domain.usecase.remote

import com.app.domain.model.Article
import com.app.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetMostEmailedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(period: Int): Flow<Result<List<Article>>> = articlesRepository.getMostEmailedArticles(period)
}
