package io.github.gunkim.domain

import java.time.LocalDateTime

class Tag(
    val id: Int? = null,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)