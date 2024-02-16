package com.challenge.nyarticles.di

import com.challenge.data.di.provideHttpClient
import com.challenge.data.network.api.ApiService
import com.challenge.data.network.implementation.ApiServiceImplementation
import org.koin.dsl.module

val networkModule =
    module {
        single<ApiService> {
            ApiServiceImplementation(httpClient = provideHttpClient())
        }
    }
