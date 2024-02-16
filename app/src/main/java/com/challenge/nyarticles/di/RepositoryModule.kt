package com.challenge.nyarticles.di

import com.challenge.data.datasource.LocalDataSource
import com.challenge.data.datasource.RemoteDataSource
import com.challenge.data.repository.impl.ArticleRepositoryImpl
import com.challenge.domain.repository.ArticlesRepository
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
