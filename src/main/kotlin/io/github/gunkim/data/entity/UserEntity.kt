package io.github.gunkim.data.entity

import io.github.gunkim.data.ArticleEntity
import io.github.gunkim.data.table.ArticleFavoriteTable
import io.github.gunkim.data.table.FollowerTable
import io.github.gunkim.data.table.FollowingTable
import io.github.gunkim.data.table.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.time.LocalDateTime
import java.util.UUID

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var name: String by UserTable.name
    var email: String by UserTable.email
    var password: String by UserTable.password
    var bio: String? by UserTable.bio
    var image: String? by UserTable.image
    val following: SizedIterable<UserEntity> by UserEntity.via(FollowingTable.userId, FollowingTable.followingId)
    val followers: SizedIterable<UserEntity> by UserEntity.via(FollowerTable.userId, FollowerTable.followerId)
    val favoriteArticles: SizedIterable<ArticleEntity> by ArticleEntity
        .via(ArticleFavoriteTable.userId, ArticleFavoriteTable.articleId)
    var createdAt: LocalDateTime by UserTable.createdAt
    var updatedAt: LocalDateTime by UserTable.updatedAt
}
