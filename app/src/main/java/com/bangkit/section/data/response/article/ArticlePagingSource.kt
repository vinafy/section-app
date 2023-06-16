package com.bangkit.section.data.response.article

import android.content.ContentValues
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.section.data.network.ApiService
import com.bangkit.section.data.response.forum.ListForum

class ArticlePagingSource(
    private val apiService: ApiService,
    private val keyword: String
) : PagingSource<Int, ListArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListArticle> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getArticle(keyword,page)
            if (response.status) {
                val forums = response.data
                Log.e(ContentValues.TAG, "load: ${response.data}")
                LoadResult.Page(
                    data = forums,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (forums.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to fetch stories"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}