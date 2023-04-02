package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object CommentTable : IntIdTable() {
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)
    val authorId: Column<EntityID<UUID>> = reference("author_id", UserTable)
    val content: Column<String> = text("content")
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").default(LocalDateTime.now())
}
