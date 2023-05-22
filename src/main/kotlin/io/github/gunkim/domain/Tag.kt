package io.github.gunkim.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

class Tag(id: EntityID<Int>) : IntEntity(id) {
    var name: String by TagTable.name
    var createdAt: LocalDateTime by TagTable.createdAt

    companion object : IntEntityClass<Tag>(TagTable)
}

object TagTable : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
}
