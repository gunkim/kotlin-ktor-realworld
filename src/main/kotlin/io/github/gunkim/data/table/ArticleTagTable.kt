package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ArticleTagTable : Table() {
    val articleId: Column<EntityID<Int>> = reference("article_id", ArticleTable)
    val tagId: Column<EntityID<Int>> = reference("tag_id", TagTable)

    override val primaryKey = PrimaryKey(articleId, tagId)
}
