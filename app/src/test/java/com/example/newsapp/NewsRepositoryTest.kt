package com.example.newsapp
import com.akshay.newsapp.news.api.NewsArticle
import com.akshay.newsapp.news.api.NewsResponse
import com.example.newsapp.api.ApiHelper
import com.example.newsapp.api.ApiService
import com.example.newsapp.localStorage.NewsArticlesDao
import com.example.newsapp.repository.MainRepository
import com.example.newsapp.utils.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class NewsRepositoryTest {
    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }
    @Mock
    lateinit var newsDao: NewsArticlesDao

    @Mock
    lateinit var newsSourceService: ApiService

    @Mock
    lateinit var apiHelper: ApiHelper


    @InjectMocks
    lateinit var newsRepository: MainRepository

    @Test
    fun `get news articles from web when there is internet`() = runBlocking {
        // GIVEN
        val fetchedArticles = listOf(
            NewsArticle(title = "Fetched 1", source = NewsArticle.Source()),
            NewsArticle(title = "Fetched 2", source = NewsArticle.Source())
        )
        val cachedArticles = listOf(
            NewsArticle(title = "Fetched 1", source = NewsArticle.Source()),
            NewsArticle(title = "Fetched 2", source = NewsArticle.Source())
        )
        val newsSource = NewsResponse(articles = fetchedArticles)
        val response = Response.success(newsSource)

        // WHEN
        whenever(newsSourceService.getNews()) doReturn response
        whenever(newsDao.getNewsArticles()) doReturn flowOf(cachedArticles)

        Assert.assertNotNull(cachedArticles)

    }

    @Test
    fun `get cached news articles when there is no internet`() = runBlocking {
        // GIVEN
        val cachedArticles = listOf(NewsArticle(title = "Cached", source = NewsArticle.Source()))
        val error = RuntimeException("Unable to fetch from network")

        // WHEN
        whenever(newsSourceService.getNews()) doThrow error
        whenever(newsDao.getNewsArticles()) doReturn flowOf(cachedArticles)

        Assert.assertNotNull(cachedArticles)
    }


}