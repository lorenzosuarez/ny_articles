package com.app.domain.usecase.local

import com.app.domain.model.Article
import com.app.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetArticleByIdUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(articleId: Long): Flow<Result<Article>> = articlesRepository.getArticleById(articleId)
}
