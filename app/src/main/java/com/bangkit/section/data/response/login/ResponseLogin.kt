package com.bangkit.section.data.response.login

data class ResponseLogin(
    val error: Boolean,
    val message: String,
    val result: ResultLogin
)