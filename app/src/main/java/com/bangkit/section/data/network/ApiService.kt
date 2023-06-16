package com.bangkit.section.data.network

import com.bangkit.section.data.response.article.ResponseArticle
import com.bangkit.section.data.response.forum.ResponseForum
import com.bangkit.section.data.response.forum.ResponsePostForum
import com.bangkit.section.data.response.login.ResponseLogin
import com.bangkit.section.data.response.logout.ResponseLogout
import com.bangkit.section.data.response.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postRegister(
        @Field("full_name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseRegister>

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseLogin>

    @GET("forum")
    suspend fun getForumPosts(
        @Query("page") page: Int
    ): ResponseForum

    @GET("/logout/user")
    suspend fun logoutUser(): Response<ResponseLogout>

    @FormUrlEncoded
    @POST("forum")
    suspend fun postForum(
        @Field("id_user") id: Int,
        @Field("theme_dcs") theme: String,
        @Field("body_dcs") body: String
    ): Response<ResponsePostForum>

    @GET("article")
    suspend fun getArticle(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): ResponseArticle
}