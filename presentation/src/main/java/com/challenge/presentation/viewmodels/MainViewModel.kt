package com.challenge.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.domain.model.Article
import com.challenge.domain.usecase.GetMostEmailedArticlesUseCase
import com.challenge.domain.usecase.GetMostSharedArticlesUseCase
import com.challenge.domain.usecase.GetMostViewedArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMostEmailedArticlesUseCase: GetMostEmailedArticlesUseCase,
    private val getMostSharedArticlesUseCase: GetMostSharedArticlesUseCase,
    private val getMostViewedArticlesUseCase: GetMostViewedArticlesUseCase,
) : ViewModel() {
    private val _mostEmailedArticles = MutableStateFlow<List<Article>>(emptyList())
    val mostEmailedArticles: StateFlow<List<Article>> = _mostEmailedArticles.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getMostEmailedArticles()
    }

    private fun getMostEmailedArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _mostEmailedArticles.update { getMostEmailedArticlesUseCase(period = 7) }
            } catch (e: Exception) {
                val er = e
                // Manejar la excepción adecuadamente, por ejemplo, exponiendo otro StateFlow para errores.
            } finally {
                _isLoading.value = false
            }
        }
    }
}
