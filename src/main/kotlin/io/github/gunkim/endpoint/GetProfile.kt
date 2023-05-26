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

fun Route.getProfile() = get("/api/profiles/{username}") {
    val principal = call.principal<JWTPrincipal>()
    val username = call.parameters["username"]!!

    val findUser = transaction {
        User.find { UserTable.name eq username }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")
    }
    val isFollowing = principal?.run {
        transaction {
            User.find { UserTable.id eq userId }
                .firstOrNull()
                ?.following
                ?.contains(findUser)
        }
    } ?: false

    call.respond(GetProfileResponse(findUser, isFollowing))
}

@Serializable
data class GetProfileResponse(
    val profile: GetProfileBodyResponse
) {
    constructor(user: User, isFollowing: Boolean) : this(
        GetProfileBodyResponse(
            user.name,
            user.bio,
            user.image,
            isFollowing
        )
    )
}

@Serializable
data class GetProfileBodyResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)

