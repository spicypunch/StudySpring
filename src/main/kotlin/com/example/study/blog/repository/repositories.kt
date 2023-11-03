package com.example.study.blog.repository

import com.example.study.entitiy.Wordcount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<Wordcount, String>