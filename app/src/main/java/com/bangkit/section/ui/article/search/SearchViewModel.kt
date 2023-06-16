package com.bangkit.section.ui.article.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.bangkit.section.data.SectionRepository
import com.bangkit.section.data.response.article.ListArticle

class SearchViewModel (
    private val repository: SectionRepository
) : ViewModel() {
    fun searchArticles(keyword: String): LiveData<PagingData<ListArticle>> {
        return repository.getArticle(keyword = keyword)
    }
}