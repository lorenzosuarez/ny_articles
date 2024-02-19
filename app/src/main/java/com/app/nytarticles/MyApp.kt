package com.app.nytarticles

import android.app.Application
import com.app.nytarticles.di.appModule
import com.app.nytarticles.di.databaseModule
import com.app.nytarticles.di.networkModule
import com.app.nytarticles.di.repositoryModule
import com.app.nytarticles.di.useCaseModule
import com.app.nytarticles.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    appModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                ),
            )
        }
    }
}
