package com.example.newsapp.api

import com.akshay.newsapp.news.api.NewsResponse
import retrofit2.Response
import javax.inject.Inject
class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{

    override suspend fun getNews(): Response<NewsResponse> = apiService.getNews()

}