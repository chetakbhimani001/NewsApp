package com.example.newsapp.repository

import com.akshay.newsapp.news.api.NewsArticle
import com.akshay.newsapp.news.api.NewsResponse
import com.example.newsapp.api.ApiHelper
import com.example.newsapp.api.ApiService
import com.example.newsapp.localStorage.NewsArticlesDao
import com.example.newsapp.localStorage.NewsDatabase
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.httpError
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject
interface NewsRepository {

    /**
     * Gets tne cached news article from database and tries to get
     * fresh news articles from web and save into database
     * if that fails then continues showing cached data.
     */
    fun getNewsArticles(): Flow<Resource<List<NewsArticle>>>

    /**
     * Gets fresh news from web.
     */
    suspend fun getNewsFromWebservice(): Response<NewsResponse>
}

class MainRepository @Inject constructor(
    private val apiHelper:ApiHelper, val newsDao: NewsArticlesDao
): NewsRepository{

    override fun getNewsArticles(): Flow<Resource<List<NewsArticle>>> = flow {

//        cachedNews.map {
//            if(it.isNullOrEmpty())
//            {
                val freshNews = getNewsFromWebservice()
                freshNews.body()?.articles?.let(newsDao::clearAndCacheArticles)
            //}
        val cachedNews = newsDao.getNewsArticles()

        //}
        emitAll(cachedNews.map { Resource.success(it) })
    } .flowOn(Dispatchers.IO)

    override suspend fun getNewsFromWebservice(): Response<NewsResponse> {
        return try {
            apiHelper.getNews()
        } catch (e: Exception) {
            e.printStackTrace()
            httpError(404)
        }
    }


}
@Module
@InstallIn(ApplicationComponent::class)
interface NewsRepositoryModule {
    @Binds
    fun it(it: MainRepository): NewsRepository
}