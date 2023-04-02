package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TagTable : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
    val createdAt: Column<LocalDateTime> = datetime("created_at").default(LocalDateTime.now())
}
