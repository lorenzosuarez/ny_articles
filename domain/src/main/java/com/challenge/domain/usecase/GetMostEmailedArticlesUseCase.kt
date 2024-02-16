package com.challenge.domain.usecase

import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository

class GetMostEmailedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(period: Int): List<Article> = articlesRepository.getMostEmailedArticles(period)
}
