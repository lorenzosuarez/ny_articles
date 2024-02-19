package com.app.nytarticles.di

import com.app.data.datasource.LocalDataSource
import com.app.data.datasource.RemoteDataSource
import com.app.data.repository.impl.ArticleRepositoryImpl
import com.app.domain.repository.ArticlesRepository
import org.koin.dsl.module

val repositoryModule =
    module {
        single { RemoteDataSource(apiService = get()) }
        single { LocalDataSource(articleDao = get()) }

        single<ArticlesRepository> {
            ArticleRepositoryImpl(
                remoteDataSource = get(),
                localDataSource = get(),
            )
        }
    }
