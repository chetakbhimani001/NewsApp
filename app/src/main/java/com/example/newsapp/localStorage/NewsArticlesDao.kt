package com.example.newsapp.localStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.akshay.newsapp.news.api.NewsArticle
import kotlinx.coroutines.flow.Flow

/**
 * Defines access layer to news articles table
 */
@Dao
interface NewsArticlesDao {

    /**
     * Insert articles into the table
     */
    @Insert
    fun insertArticles(articles: List<NewsArticle>): List<Long>

    @Query("DELETE FROM Articles")
    fun clearAllArticles()

    @Transaction
    fun clearAndCacheArticles(articles: List<NewsArticle>) {
        clearAllArticles()
        insertArticles(articles)
    }

    /**
     * Get all the articles from table
     */
    @Query("SELECT * FROM Articles")
    fun getNewsArticles(): Flow<List<NewsArticle>>
}