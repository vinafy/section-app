package com.bangkit.section.data.response.login

data class ResultLogin(
    val id_user: String,
    val full_name: String,
    val profile_picture: String,
    val token: String
)