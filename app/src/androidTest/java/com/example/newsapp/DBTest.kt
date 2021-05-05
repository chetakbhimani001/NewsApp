package com.example.newsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshay.newsapp.news.api.NewsArticle
import com.example.newsapp.localStorage.NewsArticlesDao
import com.example.newsapp.localStorage.NewsDatabase
import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DBTest : TestCase() {
    private lateinit var db: NewsDatabase
    private lateinit var dao: NewsArticlesDao

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        dao = db.newsArticlesDao()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is not null -- if the item is present then our test cases pass
    @Test
    fun insertArticles() = runBlocking {
        val articles =  mutableListOf<NewsArticle>()
        val article = NewsArticle(0, NewsArticle.Source("1","cnbc"),"","","","","","","")
        articles.addAll(listOf(article))
        dao.insertArticles(articles)
        val news = dao.getNewsArticles()

        news.collect {
            assertTrue(it.isNotEmpty())
        }

    }
}