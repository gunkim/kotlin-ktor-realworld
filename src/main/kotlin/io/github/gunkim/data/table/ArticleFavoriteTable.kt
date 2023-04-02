package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object ArticleFavoriteTable : Table() {
    val userId: Column<EntityID<UUID>> = reference("user_id", UserTable)
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)

    override val primaryKey = PrimaryKey(articleId, userId)
}
