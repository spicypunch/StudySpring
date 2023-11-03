package com.example.study.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Wordcount (
        @Id val word: String,
        val cnt: Int = 0
)