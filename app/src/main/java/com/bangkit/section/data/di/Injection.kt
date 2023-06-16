package com.bangkit.section.data.di

import android.content.Context
import com.bangkit.section.data.SectionRepository
import com.bangkit.section.data.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): SectionRepository {
        val apiservice = ApiConfig.getApiService(context)
        return SectionRepository(apiservice)
    }
}