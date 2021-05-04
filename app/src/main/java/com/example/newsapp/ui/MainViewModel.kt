package com.example.newsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.akshay.newsapp.news.api.NewsArticle
import com.akshay.newsapp.news.api.NewsResponse
import com.example.newsapp.utils.Resource
import com.example.newsapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
):ViewModel(){

    private val newsArticleDb: LiveData<Resource<List<NewsArticle>>> = mainRepository.getNewsArticles().asLiveData()

    /**
     * Return news articles to observeNotNull on the UI.
     */
    fun getNewsArticles(): LiveData<Resource<List<NewsArticle>>> = newsArticleDb
}