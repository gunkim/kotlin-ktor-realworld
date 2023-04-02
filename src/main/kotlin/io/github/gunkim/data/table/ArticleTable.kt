package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object ArticleTable : IntIdTable() {
    val authorId: Column<EntityID<UUID>> = reference("author_id", UserTable)
    val title: Column<String> = varchar("title", 50)
    val description: Column<String> = varchar("description", 100)
    val content: Column<String> = text("content")
    val slug: Column<String> = varchar("slug", 50)
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").default(LocalDateTime.now())
}
