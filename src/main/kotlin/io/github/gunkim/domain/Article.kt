package io.github.gunkim.domain

import java.time.LocalDateTime

class Article(
    val id: Int? = null,
    val author: User,
    val description: String,
    val slug: String,
    val title: String,
    val content: String,
    val tags: List<Tag>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)