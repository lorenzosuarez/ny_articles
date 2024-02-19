package com.app.nytarticles.di

import com.app.domain.usecase.local.GetArticleByIdUseCase
import com.app.domain.usecase.remote.GetMostEmailedArticlesUseCase
import com.app.domain.usecase.remote.GetMostSharedArticlesUseCase
import com.app.domain.usecase.remote.GetMostViewedArticlesUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        factory { GetArticleByIdUseCase(get()) }
        factory { GetMostSharedArticlesUseCase(get()) }
        factory { GetMostEmailedArticlesUseCase(get()) }
        factory { GetMostViewedArticlesUseCase(get()) }
    }
