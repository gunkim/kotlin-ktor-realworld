package io.github.gunkim.data

import io.github.gunkim.data.entity.UserEntity
import io.github.gunkim.data.table.ArticleTable
import io.github.gunkim.data.table.ArticleTagTable
import io.github.gunkim.data.table.CommentTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.time.LocalDateTime

class ArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ArticleEntity>(ArticleTable)

    var author: UserEntity by UserEntity referencedOn ArticleTable.authorId
    var title: String by ArticleTable.title
    var description: String by ArticleTable.description
    var content: String by ArticleTable.content
    var slug: String by ArticleTable.slug
    val tags: SizedIterable<TagEntity> by TagEntity.via(ArticleTagTable.articleId, ArticleTagTable.tagId)
    val comments: SizedIterable<CommentEntity> by CommentEntity referrersOn CommentTable.articleId
    var createdAt: LocalDateTime by ArticleTable.createdAt
    var updatedAt: LocalDateTime by ArticleTable.updatedAt
}
