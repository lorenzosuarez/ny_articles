package com.challenge.domain.usecase.local

import com.challenge.domain.model.Article
import com.challenge.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetArticleByIdUseCase(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(articleId: Long): Flow<Result<Article>> = articlesRepository.getArticleById(articleId)
}
