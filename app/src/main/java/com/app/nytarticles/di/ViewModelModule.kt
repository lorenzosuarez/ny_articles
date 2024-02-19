package com.app.nytarticles.di

import com.app.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel {
            MainViewModel(
                getArticleByIdUseCase = get(),
                getMostEmailedArticlesUseCase = get(),
                getMostSharedArticlesUseCase = get(),
                getMostViewedArticlesUseCase = get(),
            )
        }
    }
