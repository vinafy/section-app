package com.bangkit.section.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.section.data.network.ApiService
import com.bangkit.section.data.response.article.ArticlePagingSource
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.data.response.article.ResponseArticle
import com.bangkit.section.data.response.forum.ForumPagingSource
import com.bangkit.section.data.response.forum.ListForum
import com.bangkit.section.data.response.forum.ResponsePostForum
import com.bangkit.section.data.response.login.ResponseLogin
import com.bangkit.section.data.response.logout.ResponseLogout
import com.bangkit.section.data.response.register.ResponseRegister

class SectionRepository(private val apiService: ApiService) {
    fun postRegister(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<ResponseRegister>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, password)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Response body is null"))
                }
            } else {
                emit(Result.Error("Data tidak valid!"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun postLogin(
        email: String,
        password: String
    ): LiveData<Result<ResponseLogin>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Email atau Password tidak boleh kosong!"))
                }
            } else {
                if (response.code() == 400) {
                    emit(Result.Error("Email Atau Password Salah!"))
                } else {
                    emit(Result.Error("Terjadi Kesalahan"))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalaham"))
        }
    }

    fun postLogout(): LiveData<Result<ResponseLogout>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.logoutUser()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Response body is null"))
                }
            } else {
                emit(Result.Error("Unsuccessful response: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun getForumPosts(): LiveData<PagingData<ListForum>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ForumPagingSource(apiService) }
        ).liveData
    }

    fun postDiscusion(
        id: Int,
        theme: String,
        body: String
    ): LiveData<Result<ResponsePostForum>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postForum(id, theme, body)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Response body is null"))
                }
            } else {
                emit(Result.Error("Unsuccessful response: ${response.message()}${response.raw()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }
    fun getArticle(keyword: String): LiveData<PagingData<ListArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ArticlePagingSource(apiService,keyword) }
        ).liveData
    }
    fun getRecommendation(keyword: String, page: Int = 1): LiveData<Result<ResponseArticle>> = liveData {
    emit(Result.Loading)
        try {
            val response = apiService.getArticle(keyword,page)
            emit(Result.Success(response))
        }catch(e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}