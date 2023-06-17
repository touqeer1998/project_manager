package com.example.projectmanager.login.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String? = null,
    val image: String? = null,
    val phone: Long? = null,
    val token: String? = null
)
