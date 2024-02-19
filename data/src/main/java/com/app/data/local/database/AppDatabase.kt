package com.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.data.local.Converters
import com.app.data.local.dao.ArticleDao
import com.app.data.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
