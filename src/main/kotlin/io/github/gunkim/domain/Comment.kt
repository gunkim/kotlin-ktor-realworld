package io.github.gunkim.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

class Comment(id: EntityID<Int>) : IntEntity(id) {
    var content: String by CommentTable.content
    val createdAt: LocalDateTime by CommentTable.createdAt
    var updatedAt: LocalDateTime by CommentTable.updatedAt

    companion object : IntEntityClass<Comment>(CommentTable)
}

object CommentTable : IntIdTable() {
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)
    val authorId: Column<EntityID<UUID>> = reference("author_id", UserTable)
    val content: Column<String> = text("content")
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").default(LocalDateTime.now())
}
