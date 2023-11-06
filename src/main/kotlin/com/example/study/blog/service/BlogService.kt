package com.example.study.blog.service

import com.example.study.blog.dto.BlogDto
import com.example.study.blog.repository.WordRepository
import com.example.study.core.exception.InvalidInputException
import com.example.study.entitiy.Wordcount
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper.EMPTY

@Service
class BlogService(
        val wordRepository: WordRepository
) {
    @Value("\${REST_API_KEY}")
    lateinit var restApiKey: String

    fun searchKakao(blogDto: BlogDto): String? {
        val webClient = WebClient
                .builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()

        val response = webClient
                .get()
                .uri {
                    it.path("/v2/search/blog")
                            .queryParam("query", blogDto.query)
                            .queryParam("sort", blogDto.sort)
                            .queryParam("page", blogDto.page)
                            .queryParam("size", blogDto.size)
                            .build()
                }.header("Authorization", "KakaoAK $restApiKey")
                .retrieve()
                .bodyToMono<String>()

        val lowQuery: String = blogDto.query.lowercase()
        val word: Wordcount = wordRepository.findById(lowQuery).orElse(Wordcount(lowQuery))
        word.cnt++

        wordRepository.save(word)

        return response.block()
    }

    fun searchWordRank(): List<Wordcount> = wordRepository.findTo10ByOrderByCntDesc()
}