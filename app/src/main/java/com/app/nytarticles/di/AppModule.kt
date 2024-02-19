package com.app.nytarticles.di

import com.app.presentation.utils.AndroidResourceProvider
import com.app.presentation.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule =
    module {
        single<ResourceProvider> {
            AndroidResourceProvider(androidContext())
        }
    }
