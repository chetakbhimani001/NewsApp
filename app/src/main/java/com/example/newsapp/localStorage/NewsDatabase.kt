package com.example.newsapp.localStorage

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akshay.newsapp.news.api.NewsArticle

@Database(
        entities = [NewsArticle::class],
        version = 1
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    /**
     * Get news article DAO
     */
    abstract fun newsArticlesDao(): NewsArticlesDao

    companion object {

        private const val databaseName = "news-db"

        fun buildDefault(context: Context) =
                Room.databaseBuilder(context, NewsDatabase::class.java, databaseName)
                        .fallbackToDestructiveMigration()
                        .build()

        @VisibleForTesting
        fun buildTest(context: Context) =
                Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
                        .build()
    }
}