package com.challenge.domain.usecase

import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository

class GetMostSharedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(
        period: Int,
        shareType: String,
    ): List<Article> = articlesRepository.getMostSharedArticles(period, shareType)
}
