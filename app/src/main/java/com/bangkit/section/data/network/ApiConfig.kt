package com.bangkit.section.data.network

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.bangkit.section.data.preference.UserPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

        companion object {
            private fun setInterceptor(apiKey: String?): OkHttpClient {
                val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                return if (apiKey.isNullOrEmpty()) {
                    OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build()
                } else {
                    OkHttpClient.Builder()
                        .addInterceptor(Header(apiKey))
                        .addInterceptor(loggingInterceptor)
                        .build()
                }
            }

            fun getApiService(context: Context): ApiService {
                val sharedPref = UserPreference.initPref(context, "onSignIn")
                val apiKey = sharedPref.getString("apiKey", null).toString()
                Log.e(TAG, "getApiService: $apiKey", )
                val BASE_URL = "http://34.101.137.177/section-app/index.php/api/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(setInterceptor(apiKey))
                    .build()
                return retrofit.create(ApiService::class.java)
            }
        }
}