package com.challenge.nyarticles.di

import com.challenge.presentation.utils.AndroidResourceProvider
import com.challenge.presentation.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule =
    module {
        single<ResourceProvider> {
            AndroidResourceProvider(androidContext())
        }
    }
