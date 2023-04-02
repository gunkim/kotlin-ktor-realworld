package io.github.gunkim.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object FollowingTable : Table() {
    val userId: Column<EntityID<UUID>> = reference("user_id", UserTable)
    val followingId: Column<EntityID<UUID>> = reference("following_id", UserTable)

    override val primaryKey = PrimaryKey(userId, followingId)
}
