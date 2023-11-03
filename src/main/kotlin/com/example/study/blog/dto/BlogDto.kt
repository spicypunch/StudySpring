package com.example.study.blog.dto

data class BlogDto(
        val query: String,
        val sort: String,
        val pade: Int, val size: Int
)