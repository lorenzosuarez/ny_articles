package com.challenge.nyarticles

import android.app.Application
import com.challenge.nyarticles.di.appModule
import com.challenge.nyarticles.di.databaseModule
import com.challenge.nyarticles.di.networkModule
import com.challenge.nyarticles.di.repositoryModule
import com.challenge.nyarticles.di.useCaseModule
import com.challenge.nyarticles.di.viewModelModule
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
