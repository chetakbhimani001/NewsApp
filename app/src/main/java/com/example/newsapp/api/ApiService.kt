package com.example.newsapp.api

import com.akshay.newsapp.news.api.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService{

    @GET("top-headlines?apiKey=ad3ab5797d43468999b55c597bf2190a&category=technology")
    suspend fun getNews(): Response<NewsResponse>


}