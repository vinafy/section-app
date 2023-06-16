package com.bangkit.section.data.response.forum

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.section.data.network.ApiService

class ForumPagingSource(private val apiService: ApiService) : PagingSource<Int, ListForum>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListForum> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getForumPosts(page)

            if (response.status) {
                val forums = response.data
                Log.e(TAG, "load: ${response.data}", )
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

    override fun getRefreshKey(state: PagingState<Int, ListForum>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}




