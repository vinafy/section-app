package com.bangkit.section.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.data.response.forum.ListForum
import com.bangkit.section.databinding.RvActicleBinding
import com.bangkit.section.databinding.RvPostinganBinding
import com.bumptech.glide.Glide

class ArticleAdapter :
    PagingDataAdapter<ListArticle, ArticleAdapter.ArticleViewHolder>(diffCallback) {
    private var clickListener: OnAdapterClickCallback? = null

    fun setOnItemClickCallback(onAdapterClickCallback: OnAdapterClickCallback) {
        this.clickListener = onAdapterClickCallback
    }

    inner class ArticleViewHolder(val binding: RvActicleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ListArticle) {
            article.let { article ->
                binding.apply {
                    titleArticle.text = article.Title
                    reviewer.text = article.Reviewer
                    time.text = article.Date
                        itemView.setOnClickListener {
                            clickListener?.onItemClick(article)
                        }
                }
            }
        }
    }

    interface OnAdapterClickCallback {
        fun onItemClick(data: ListArticle)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = RvActicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ListArticle>() {
            override fun areItemsTheSame(oldItem: ListArticle, newItem: ListArticle): Boolean {
                return oldItem.Article_id == newItem.Article_id
            }

            override fun areContentsTheSame(oldItem: ListArticle, newItem: ListArticle): Boolean {
                return oldItem == newItem
            }

        }
    }
}