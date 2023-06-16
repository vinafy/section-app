package com.bangkit.section.ui.forum.posting

import androidx.lifecycle.ViewModel
import com.bangkit.section.data.SectionRepository

class PostingViewModel(private val sectionRepository: SectionRepository) : ViewModel() {
    fun postDiscussion(id: Int,theme: String,body:String) = sectionRepository.postDiscusion(id, theme, body)
}