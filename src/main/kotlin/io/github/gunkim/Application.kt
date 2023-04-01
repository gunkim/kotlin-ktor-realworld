package io.github.gunkim

import io.github.gunkim.application.ktor.plugins.configureDatabases
import io.github.gunkim.application.ktor.plugins.configureMonitoring
import io.github.gunkim.application.ktor.plugins.configureRouting
import io.github.gunkim.application.ktor.plugins.configureSecurity
import io.github.gunkim.application.ktor.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
