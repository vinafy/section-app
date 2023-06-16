package com.bangkit.section.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Header(private var apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req: Request = chain.request()
        req = if (req.header("No-Authentication") == null && apiKey.isNotEmpty()) {
            req.newBuilder()
                .addHeader("Authorization", apiKey)
                .build()
        } else {
            req.newBuilder()
                .build()
        }
        return chain.proceed(req)
    }
}