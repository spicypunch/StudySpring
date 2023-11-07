package com.example.study.blog.controller

import com.example.study.blog.dto.BlogDto
import com.example.study.blog.service.BlogService
import com.example.study.entitiy.Wordcount
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/blog")
@RestController
class BlogController(
        val blogService: BlogService
) {
//        @GetMapping("")
//        fun search(@RequestBody @Valid blogDto: BlogDto): String? {
//            return blogService.searchKakao(blogDto)
//        }
    @GetMapping("")
    fun search(@RequestParam query: String, @RequestParam sort: String, @RequestParam page: Int, @RequestParam size: Int): String? {
        val blogDto = BlogDto(query, sort, page, size)
        return blogService.searchKakao(blogDto)
    }


    @GetMapping("/rank")
    fun searchWordRank(): List<Wordcount> = blogService.searchWordRank()
}