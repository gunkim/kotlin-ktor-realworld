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

fun Route.unfollowUser() = delete("/api/profiles/{username}/follow") {
    val userId = call.principal<JWTPrincipal>()!!.userId
    val username = call.parameters["username"]!!

    val user = transaction {
        val me = User.find { UserTable.id eq userId }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")

        val target = User.find { UserTable.name eq username }
            .firstOrNull()
            ?: throw IllegalArgumentException("User not found.")

        me.unfollow(target)

        target
    }

    call.respond(UnfollowUserResponse(user, false))
}


@Serializable
data class UnfollowUserResponse(
    val profile: UnfollowUserBodyResponse
) {
    constructor(user: User, isFollowing: Boolean) : this(
        UnfollowUserBodyResponse(
            user.name,
            user.bio,
            user.image,
            isFollowing
        )
    )
}

@Serializable
data class UnfollowUserBodyResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
