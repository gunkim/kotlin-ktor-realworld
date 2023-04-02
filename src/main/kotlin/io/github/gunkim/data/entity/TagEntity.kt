package io.github.gunkim.data

import io.github.gunkim.data.table.TagTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class TagEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TagEntity>(TagTable)

    var name: String by TagTable.name
    var createdAt: LocalDateTime by TagTable.createdAt
}
