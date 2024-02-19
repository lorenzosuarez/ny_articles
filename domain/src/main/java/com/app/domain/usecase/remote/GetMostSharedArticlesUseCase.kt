package com.app.domain.usecase.remote

import com.app.domain.model.Article
import com.app.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetMostSharedArticlesUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(
        period: Int,
        shareType: String = "facebook",
    ): Flow<Result<List<Article>>> = articlesRepository.getMostSharedArticles(period, shareType)
}
