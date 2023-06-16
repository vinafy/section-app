package com.bangkit.section.ui.auth.login

import androidx.lifecycle.ViewModel
import com.bangkit.section.data.SectionRepository

class LoginViewModel(private val sectionRepository: SectionRepository) : ViewModel() {
 fun login(email: String, password: String) = sectionRepository.postLogin(email,password)
}