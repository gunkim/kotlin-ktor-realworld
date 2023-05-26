package io.github.gunkim.endpoint

import io.github.gunkim.common.userId
import io.github.gunkim.domain.User
import io.github.gunkim.domain.UserTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Route.updateUser(createToken: (UUID) -> String) = put("/api/user") {
    val userId = call.principal<JWTPrincipal>()!!.userId
    val request = call.receive<UpdateUserRequest>().user

    val updatedUser = transaction {
        User.find { UserTable.id eq userId }
            .firstOrNull()
            ?.apply {
                update(
                    email = request.email,
                    name = request.username,
                    password = request.password,
                    bio = request.bio,
                    image = request.image
                )
            }
            ?: throw IllegalArgumentException("User not found.")
    }
    call.respond(UpdateUserResponse(updatedUser, createToken(userId)))
}

@Serializable
data class UpdateUserRequest(
    val user: UpdateUserBodyRequest
)

@Serializable
data class UpdateUserBodyRequest(
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val bio: String? = null,
    val image: String? = null,
)

@Serializable
data class UpdateUserResponse(
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
data class UpdateUserBodyResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?,
)
