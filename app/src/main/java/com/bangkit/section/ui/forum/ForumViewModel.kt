package com.bangkit.section.ui.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.section.data.SectionRepository
import com.bangkit.section.data.response.forum.ListForum

class ForumViewModel (private val sectionRepository: SectionRepository) : ViewModel() {
    fun getStories(): LiveData<PagingData<ListForum>> {
        return sectionRepository.getForumPosts()
    }
}