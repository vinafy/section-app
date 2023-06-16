package com.bangkit.section.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.section.data.SectionRepository

class ProfileViewModel (private val sectionRepository: SectionRepository) : ViewModel(){
    fun logout() = sectionRepository.postLogout()
}