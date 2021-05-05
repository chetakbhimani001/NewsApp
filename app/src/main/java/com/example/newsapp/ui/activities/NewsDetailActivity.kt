package com.example.newsapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.akshay.newsapp.news.api.NewsArticle
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.utils.Constants
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*


class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar_layout.setContentScrimColor(resources.getColor(R.color.black));

        toolbar.setNavigationOnClickListener { onBackPressed() }
        intent.getParcelableExtra<NewsArticle>(Constants.INTENT_DATA)?.let {
                findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = it.source.name
                txtTitle.text = it.title
                txtDescription.text = it.content
                Glide.with(this)
                    .load(it.urlToImage)
                    .into(imgNews)

            }

    }
}