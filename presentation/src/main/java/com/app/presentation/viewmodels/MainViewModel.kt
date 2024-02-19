package com.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.Article
import com.app.domain.model.ArticleFilter
import com.app.domain.model.Period
import com.app.domain.usecase.local.GetArticleByIdUseCase
import com.app.domain.usecase.remote.GetMostEmailedArticlesUseCase
import com.app.domain.usecase.remote.GetMostSharedArticlesUseCase
import com.app.domain.usecase.remote.GetMostViewedArticlesUseCase
import com.app.presentation.event.UIEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val getMostEmailedArticlesUseCase: GetMostEmailedArticlesUseCase,
    private val getMostSharedArticlesUseCase: GetMostSharedArticlesUseCase,
    private val getMostViewedArticlesUseCase: GetMostViewedArticlesUseCase,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article.asStateFlow()

    private val _selectedFilter = MutableStateFlow(ArticleFilter.EMAILED)
    val selectedFilter: StateFlow<ArticleFilter> = _selectedFilter.asStateFlow()

    private val _selectedPeriod = MutableStateFlow(Period.One.days)
    val selectedPeriod: StateFlow<Period> =
        _selectedPeriod
            .map { days ->
                return@map when (days) {
                    Period.One.days -> Period.One
                    Period.Seven.days -> Period.Seven
                    Period.Thirty.days -> Period.Thirty
                    else -> Period.One
                }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Period.One)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        combine(_selectedFilter, _selectedPeriod) { filter, period ->
            Pair(filter, period)
        }.onEach { (filter, period) ->
            getArticles(filter, period)
        }.launchIn(viewModelScope)
    }

    fun updateSelectedPeriod(newPeriod: Int) {
        _selectedPeriod.update { newPeriod }
    }

    fun updateSelectedFilter(newFilter: ArticleFilter) {
        _selectedFilter.update { newFilter }
    }

    fun getArticleById(articleId: Long) {
        viewModelScope.launch {
            getArticleByIdUseCase(articleId).collect { result ->
                result.onSuccess { article ->
                    _article.update { article }
                }.onFailure { exception ->
                    _uiEvent.emit(UIEvent.ShowErrorMessage(exception.message ?: "Unknown error"))
                }
            }
        }
    }

    fun openLink(url: String) {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.OpenUrl(url))
        }
    }

    private fun getArticles(
        filter: ArticleFilter,
        period: Int,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(600) // I do this so I can see the charging status

            when (filter) {
                ArticleFilter.EMAILED -> getMostEmailedArticlesUseCase(period)
                ArticleFilter.SHARED -> getMostSharedArticlesUseCase(period)
                ArticleFilter.VIEWED -> getMostViewedArticlesUseCase(period)
            }.collect { result ->
                result.onSuccess { articles ->
                    _articles.value = articles
                    _isLoading.value = false
                }.onFailure { e ->
                    _uiEvent.emit(UIEvent.ShowErrorMessage(e.message ?: "Unknown error"))
                    _isLoading.value = false
                }
            }
        }
    }
}
