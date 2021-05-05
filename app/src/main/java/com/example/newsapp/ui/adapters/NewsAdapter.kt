package com.example.newsapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshay.newsapp.news.api.NewsArticle
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.ui.activities.NewsDetailActivity
import com.example.newsapp.utils.Constants.INTENT_DATA
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    inner class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<NewsArticle>(){
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(list: List<NewsArticle>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        val item = differ.currentList[position]

        holder.itemView.apply {
            tvTitle.text = "${item.title}"
            tvContent.text = "${item.content}"
            tvSource.text = "${item.source.name}"
            tvNewsPublishedAt.text = "${item.publishedAt}"
            Glide.with(holder.itemView.context)
                .load(item.urlToImage)
                .into(newsImage)
            setOnClickListener {
                val i = Intent(context, NewsDetailActivity::class.java)
                i.putExtra(INTENT_DATA,item)
                context.startActivity(i)
            }
        }

    }
}