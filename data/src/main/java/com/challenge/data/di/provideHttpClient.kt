package com.challenge.data.di

import com.challenge.data.network.api.KtorClient

fun provideHttpClient() = KtorClient.httpClient
