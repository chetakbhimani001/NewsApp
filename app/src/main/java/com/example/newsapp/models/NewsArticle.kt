package com.akshay.newsapp.news.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapp.localStorage.SourceConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Articles")
@Parcelize
data class NewsArticle(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(SourceConverter::class)
    @SerializedName("source")
    val source: Source,

    /**
     * Name of the author for the article
     */
    @SerializedName("author")
    val author: String? = null,

    /**
     * Title of the article
     */
    @SerializedName("title")
    val title: String? = null,

    /**
     * Complete description of the article
     */
    @SerializedName("description")
    val description: String? = null,

    /**
     * URL to the article
     */
    @SerializedName("url")
    val url: String? = null,

    /**
     * URL of the artwork shown with article
     */
    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    /**
     * Date-time when the article was published
     */
    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("content")
    val content: String? = null
):Parcelable {
    @Parcelize
    data class Source(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String? = null
    ):Parcelable
}