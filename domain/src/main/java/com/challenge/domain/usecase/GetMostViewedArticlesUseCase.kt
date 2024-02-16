package com.challenge.domain.usecase

import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository

class GetMostViewedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(period: Int): List<Article> = articlesRepository.getMostViewedArticles(period)
}
