package com.example.newsapp.localStorage

import androidx.room.TypeConverter
import com.akshay.newsapp.news.api.NewsArticle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceConverter {
    @TypeConverter // note this annotation
    fun fromOptionValuesList(optionValues: NewsArticle.Source?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<NewsArticle.Source?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter // note this annotation
    fun toOptionValuesList(optionValuesString: String?): NewsArticle.Source? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<NewsArticle.Source?>() {}.type
        return gson.fromJson<NewsArticle.Source>(optionValuesString, type)
    }
}