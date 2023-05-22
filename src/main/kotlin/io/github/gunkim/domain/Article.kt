package io.github.gunkim.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

class Article(id: EntityID<Int>) : IntEntity(id) {
    var author: User by User referencedOn ArticleTable.authorId
    var title: String by ArticleTable.title
    var description: String by ArticleTable.description
    var content: String by ArticleTable.content
    var slug: String by ArticleTable.slug
    val tags: SizedIterable<Tag> by Tag.via(ArticleTagTable.articleId, ArticleTagTable.tagId)
    val comments: SizedIterable<Comment> by Comment referrersOn CommentTable.articleId
    var createdAt: LocalDateTime by ArticleTable.createdAt
    var updatedAt: LocalDateTime by ArticleTable.updatedAt

    companion object : IntEntityClass<Article>(ArticleTable)
}

object ArticleTable : IntIdTable() {
    val authorId: Column<EntityID<UUID>> = reference("author_id", UserTable)
    val title: Column<String> = varchar("title", 50)
    val description: Column<String> = varchar("description", 100)
    val content: Column<String> = text("content")
    val slug: Column<String> = varchar("slug", 50)
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").default(LocalDateTime.now())
}

object ArticleTagTable : Table() {
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)
    val tagId: Column<EntityID<Int>> = reference("tag_id", TagTable)

    override val primaryKey = PrimaryKey(articleId, tagId)
}

object ArticleFavoriteTable : Table() {
    val userId: Column<EntityID<UUID>> = reference("user_id", UserTable)
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)

    override val primaryKey = PrimaryKey(articleId, userId)
}
