package com.bangkit.section.ui.auth.register

import androidx.lifecycle.ViewModel
import com.bangkit.section.data.SectionRepository

class RegisterViewModel(private val sectionRepository: SectionRepository) : ViewModel() {
    fun postRegister(name: String,email: String,password:String)= sectionRepository.postRegister(name,email,password)
}