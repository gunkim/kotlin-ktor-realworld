package io.github.gunkim.endpoint

import io.github.gunkim.domain.User
import io.github.gunkim.domain.UserTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Route.loginUser(createToken: (UUID) -> String) = post("/api/users/login") {
    val request = call.receive<LoginUserRequest>().user

    val findUser = transaction {
        User.find { (UserTable.email eq request.email) and (UserTable.password eq request.password) }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")
    }

    call.respond(LoginUserResponse(findUser, createToken(findUser.id.value)))
}

@Serializable
data class LoginUserRequest(
    val user: LoginUserBodyRequest
)

@Serializable
data class LoginUserBodyRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginUserResponse(
    val user: LoginUserBodyResponse
) {
    constructor(user: User, token: String) : this(
        LoginUserBodyResponse(
            user.email,
            token,
            user.name,
            user.bio,
            user.image
        )
    )
}

@Serializable
data class LoginUserBodyResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?,
)
