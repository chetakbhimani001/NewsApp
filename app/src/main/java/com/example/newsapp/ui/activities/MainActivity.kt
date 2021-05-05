package com.example.newsapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.viewmodels.MainViewModel
import com.example.newsapp.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar_title.setText(getString(R.string.news))

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        adapter = NewsAdapter()
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = adapter

        mainViewModel.getNewsArticles().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    rvNews.visibility = View.VISIBLE
                    it.data.let { res ->
                        if (it.status.equals(Status.SUCCESS)) {
                            res?.let { it1 -> adapter.submitList(it1) }
                        } else {
                            Snackbar.make(rootView, "Status = false", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                    rvNews.visibility = View.GONE
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    rvNews.visibility = View.VISIBLE
                    Snackbar.make(rootView, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
}