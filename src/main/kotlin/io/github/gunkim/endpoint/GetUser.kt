package io.github.gunkim.endpoint

import io.github.gunkim.common.userId
import io.github.gunkim.domain.User
import io.github.gunkim.domain.UserTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Route.getUser(createToken: (UUID) -> String) = get("/api/user") {
    val userId = call.principal<JWTPrincipal>()!!.userId

    val findUser = transaction {
        User.find { UserTable.id eq userId }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")
    }

    call.respond(GetUserResponse(findUser, createToken(userId)))
}

@Serializable
data class GetUserResponse(
    val user: GetUserBodyResponse
) {
    constructor(user: User, token: String) : this(
        GetUserBodyResponse(
            user.email,
            token,
            user.name,
            user.bio,
            user.image
        )
    )
}

@Serializable
data class GetUserBodyResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?,
)
