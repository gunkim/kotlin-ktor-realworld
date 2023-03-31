package io.github.gunkim.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val database = Database.connect(
        url = this@configureDatabases.environment.config.property("database.url").getString(),
        user = this@configureDatabases.environment.config.property("database.user").getString(),
        password = this@configureDatabases.environment.config.property("database.password").getString(),
        driver = this@configureDatabases.environment.config.property("database.driver").getString(),
    )
}
