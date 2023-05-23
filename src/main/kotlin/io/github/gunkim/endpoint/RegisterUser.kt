package io.github.gunkim.endpoint

import io.github.gunkim.domain.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Route.registerUser(createToken: (UUID) -> String) = post("/api/users") {
    val request = call.receive<RegisterUserRequest>()

    val createdUser = transaction {
        User.new {
            email = request.email
            name = request.username
            password = request.password
        }
    }

    call.respond(RegisterUserResponse(createdUser, createToken(createdUser.id.value)))
}

@Serializable
data class RegisterUserRequest(
    val email: String,
    val username: String,
    val password: String,
)

@Serializable
data class RegisterUserResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?,
) {
    constructor(user: User, token: String) : this(
        user.email,
        token,
        user.name,
        user.bio,
        user.image
    )
}
