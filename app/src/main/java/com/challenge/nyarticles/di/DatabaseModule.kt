package com.challenge.nyarticles.di

import android.app.Application
import androidx.room.Room
import com.challenge.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule =
    module {
        single { createDatabase(androidApplication()) }
        single { get<AppDatabase>().articleDao() }
    }

private fun createDatabase(app: Application): AppDatabase =
    Room.databaseBuilder(app, AppDatabase::class.java, "challenge_database")
        .fallbackToDestructiveMigration()
        .build()
