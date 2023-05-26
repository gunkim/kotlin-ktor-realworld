package io.github.gunkim.domain

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    var name: String by UserTable.name
    var email: String by UserTable.email
    var password: String by UserTable.password
    var bio: String? by UserTable.bio
    var image: String? by UserTable.image
    val following: SizedIterable<User> by User.via(FollowingTable.userId, FollowingTable.followingId)
    val followers: SizedIterable<User> by User.via(FollowerTable.userId, FollowerTable.followerId)
    val favoriteArticles: SizedIterable<Article> by Article
        .via(ArticleFavoriteTable.userId, ArticleFavoriteTable.articleId)
    var createdAt: LocalDateTime by UserTable.createdAt
    var updatedAt: LocalDateTime by UserTable.updatedAt

    fun update(
        name: String? = null,
        email: String? = null,
        password: String? = null,
        bio: String? = null,
        image: String? = null
    ) {
        name?.let { this.name = it }
        email?.let { this.email = it }
        password?.let { this.password = it }
        bio?.let { this.bio = it }
        image?.let { this.image = it }
        updatedAt = LocalDateTime.now()
    }

    companion object : UUIDEntityClass<User>(UserTable)
}

object UserTable : UUIDTable() {
    val name: Column<String> = varchar("name", 50)
    val email: Column<String> = varchar("email", 50)
    val password: Column<String> = varchar("password", 50)
    val bio: Column<String?> = varchar("bio", 50).nullable()
    val image: Column<String?> = varchar("image", 50).nullable()
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").default(LocalDateTime.now())
}

object FollowerTable : Table() {
    val userId: Column<EntityID<UUID>> = reference("user_id", UserTable)
    val followerId: Column<EntityID<UUID>> = reference("follower_id", UserTable)

    override val primaryKey = PrimaryKey(userId, followerId)
}

object FollowingTable : Table() {
    val userId: Column<EntityID<UUID>> = reference("user_id", UserTable)
    val followingId: Column<EntityID<UUID>> = reference("following_id", UserTable)

    override val primaryKey = PrimaryKey(userId, followingId)
}
