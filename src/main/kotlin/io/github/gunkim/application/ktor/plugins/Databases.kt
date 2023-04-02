package io.github.gunkim.application.ktor.plugins

import io.github.gunkim.data.table.ArticleFavoriteTable
import io.github.gunkim.data.table.ArticleTable
import io.github.gunkim.data.table.ArticleTagTable
import io.github.gunkim.data.table.CommentTable
import io.github.gunkim.data.table.FollowerTable
import io.github.gunkim.data.table.FollowingTable
import io.github.gunkim.data.table.TagTable
import io.github.gunkim.data.table.UserTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
        url = this@configureDatabases.environment.config.property("database.url").getString(),
        user = this@configureDatabases.environment.config.property("database.user").getString(),
        password = this@configureDatabases.environment.config.property("database.password").getString(),
        driver = this@configureDatabases.environment.config.property("database.driver").getString(),
    )

    transaction(database) {
        SchemaUtils.create(
            UserTable,
            ArticleTable,
            CommentTable,
            TagTable,
            ArticleTagTable,
            ArticleFavoriteTable,
            FollowingTable,
            FollowerTable,
        )
    }
}
