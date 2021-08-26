package com.mohammadosman.structuredconcurrency_kotlincoroutines.domain

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)