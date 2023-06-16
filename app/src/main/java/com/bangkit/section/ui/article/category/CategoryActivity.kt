package com.bangkit.section.ui.article.category

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.section.R
import com.bangkit.section.ReadArticleActivity
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.databinding.ActivityCategoryBinding
import com.bangkit.section.ui.adapter.ArticleAdapter
import com.bangkit.section.ui.article.search.SearchViewModel
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val kategori = intent.getStringExtra(Category)
        val textKategory = intent.getStringExtra(TextCategory)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter =articleAdapter
        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        binding.back.setOnClickListener{
            this.finish()
        }
        if (kategori == "Seksual"){
            binding.category.text = textKategory
        }else{
            binding.category.text = kategori
        }
        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnAdapterClickCallback{
            override fun onItemClick(data: ListArticle) {
                Intent(this@CategoryActivity, ReadArticleActivity::class.java).also {
                    it.putExtra(ReadArticleActivity.Article, data.Article)
                    it.putExtra(ReadArticleActivity.Title, data.Title)
                    startActivity(it)
                }
            }
        })
        if (kategori != null) {
            if (kategori == "Praktek Seksual") {
                queryArticle("Seksual")
            }else if(kategori == "Pengetahuan Seksual"){
                queryArticle("seksual")
            }else {
                queryArticle(kategori)
            }
        }
    }
    private fun queryArticle(keyword: String){
        viewModel.searchArticles(keyword).observe(this){
            lifecycleScope.launch {
                articleAdapter.submitData(it)
            }
        }
    }
    companion object {
        const val Category = ""
        const val TextCategory = ""
    }
}