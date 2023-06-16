package com.bangkit.section.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.databinding.RvActicleBinding

class RecommendationAdapter(private val clickListener: OnAdapterClickCallback) :
    RecyclerView.Adapter<RecommendationAdapter.ArticleViewHolder>() {

    private var articles: List<ListArticle> = emptyList()

    fun setArticles(articles: List<ListArticle>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(val binding: RvActicleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ListArticle) {
            binding.apply {
                titleArticle.text = article.Title
                reviewer.text = article.Reviewer
                time.text = article.Date
                itemView.setOnClickListener {
                    clickListener.onItemClick(article)
                }
            }
        }
    }

    interface OnAdapterClickCallback {
        fun onItemClick(data: ListArticle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val view = RvActicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }
}

