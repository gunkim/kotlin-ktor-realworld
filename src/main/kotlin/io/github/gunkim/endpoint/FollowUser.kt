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

fun Route.followUser() = post("/api/profiles/{username}/follow") {
    val userId = call.principal<JWTPrincipal>()!!.userId
    val username = call.parameters["username"]!!

    val user = transaction {
        val target = User.find { UserTable.name eq username }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")

        val me = User.find { UserTable.id eq userId }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")

        me.follow(target)

        target
    }

    call.respond(FollowUserResponse(user, true))
}

@Serializable
data class FollowUserResponse(
    val profile: FollowUserBodyResponse
) {
    constructor(user: User, isFollowing: Boolean) : this(
        FollowUserBodyResponse(
            user.name,
            user.bio,
            user.image,
            isFollowing
        )
    )
}

@Serializable
data class FollowUserBodyResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
