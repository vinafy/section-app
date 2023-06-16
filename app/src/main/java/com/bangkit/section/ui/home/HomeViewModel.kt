package com.bangkit.section.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.section.data.SectionRepository

class HomeViewModel (private val sectionRepository: SectionRepository) : ViewModel() {
    fun getRecommendation(query: String) = sectionRepository.getRecommendation(query,1)
}