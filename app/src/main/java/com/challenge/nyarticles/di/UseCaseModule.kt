package com.challenge.nyarticles.di

import com.challenge.domain.usecase.local.GetArticleByIdUseCase
import com.challenge.domain.usecase.remote.GetMostEmailedArticlesUseCase
import com.challenge.domain.usecase.remote.GetMostSharedArticlesUseCase
import com.challenge.domain.usecase.remote.GetMostViewedArticlesUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        factory { GetArticleByIdUseCase(get()) }
        factory { GetMostSharedArticlesUseCase(get()) }
        factory { GetMostEmailedArticlesUseCase(get()) }
        factory { GetMostViewedArticlesUseCase(get()) }
    }
