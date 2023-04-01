package io.github.gunkim.domain

import java.time.LocalDateTime

class Comment(
    val id: Int? = null,
    val article: Article,
    val user: User,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)