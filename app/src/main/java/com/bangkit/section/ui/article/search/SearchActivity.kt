package com.bangkit.section.ui.article.search

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.section.R
import com.bangkit.section.ReadArticleActivity
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.databinding.ActivityForumBinding
import com.bangkit.section.databinding.ActivitySearchBinding
import com.bangkit.section.ui.adapter.ArticleAdapter
import com.bangkit.section.ui.adapter.ForumAdapter
import com.bangkit.section.ui.forum.ForumViewModel
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter =articleAdapter
        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        binding.keywordSearch.requestFocus()
        showKeyboard()
        binding.back.setOnClickListener{
            this.finish()
        }
        binding.keywordSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.search.setOnClickListener {
            val keyword = binding.keywordSearch.text.toString()
            queryArticle(keyword)
            articleAdapter.notifyDataSetChanged()
        }
        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnAdapterClickCallback{
            override fun onItemClick(data: ListArticle) {
                Intent(this@SearchActivity,ReadArticleActivity::class.java).also {
                    it.putExtra(ReadArticleActivity.Article, data.Article)
                    it.putExtra(ReadArticleActivity.Title, data.Title)
                    startActivity(it)
                }
            }
        })
        binding.keywordSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.keywordSearch.text.toString()
                Log.e(TAG, "onCreate: $keyword", )
                queryArticle(keyword)
                articleAdapter.notifyDataSetChanged()
                true
            } else {
                false
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
    private fun showKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.keywordSearch, InputMethodManager.SHOW_IMPLICIT)
    }
}