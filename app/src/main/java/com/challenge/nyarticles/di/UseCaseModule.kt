package com.challenge.nyarticles.di

import com.challenge.domain.usecase.GetMostEmailedArticlesUseCase
import com.challenge.domain.usecase.GetMostSharedArticlesUseCase
import com.challenge.domain.usecase.GetMostViewedArticlesUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        factory { GetMostEmailedArticlesUseCase(get()) }
        factory { GetMostSharedArticlesUseCase(get()) }
        factory { GetMostViewedArticlesUseCase(get()) }
    }
