package io.github.gunkim.application.ktor.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.gunkim.endpoint.getUser
import io.github.gunkim.endpoint.loginUser
import io.github.gunkim.endpoint.registerUser
import io.github.gunkim.endpoint.updateUser
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting() {
    val createToken = { userId: UUID ->
        JWT.create()
            .withAudience(this@configureRouting.environment.config.property("jwt.audience").getString())
            .withIssuer(this@configureRouting.environment.config.property("jwt.domain").getString())
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .withClaim("userId", userId.toString())
            .sign(Algorithm.HMAC256("secret"))
    }

    routing {
        registerUser(createToken)
        loginUser(createToken)

        authenticate {
            getUser(createToken)
            updateUser(createToken)
        }
    }
}
