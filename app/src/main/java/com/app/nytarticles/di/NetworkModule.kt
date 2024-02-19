package com.app.nytarticles.di

import com.app.data.network.api.ApiService
import com.app.data.network.api.KtorClient
import com.app.data.network.implementation.ApiServiceImplementation
import org.koin.dsl.module

val networkModule =
    module {
        single { KtorClient.httpClient }
        single<ApiService> {
            ApiServiceImplementation(httpClient = get())
        }
    }
