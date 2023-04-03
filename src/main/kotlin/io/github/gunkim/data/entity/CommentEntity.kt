package io.github.gunkim.data.entity

import io.github.gunkim.data.table.CommentTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class CommentEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CommentEntity>(CommentTable)

    var content: String by CommentTable.content
    val createdAt: LocalDateTime by CommentTable.createdAt
    var updatedAt: LocalDateTime by CommentTable.updatedAt
}
