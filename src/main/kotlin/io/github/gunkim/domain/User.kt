package io.github.gunkim.domain

import java.time.LocalDateTime
import java.util.UUID

class User(
    val id: Int,
    val guid: UUID,
    val name: String,
    val email: String,
    val bio: String?,
    val image: String?,
    val followers: List<User>,
    val following: List<User>,
    val favoriteArticles: List<Article>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun follow(user: User) {
        user.followers
    }
}