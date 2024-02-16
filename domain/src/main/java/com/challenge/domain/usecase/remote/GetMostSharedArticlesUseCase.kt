package com.challenge.domain.usecase.remote

import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetMostSharedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(
        period: Int,
        shareType: String = "facebook",
    ): Flow<Result<List<Article>>> = articlesRepository.getMostSharedArticles(period, shareType)
}
