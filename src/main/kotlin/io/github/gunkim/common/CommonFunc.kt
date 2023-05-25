package io.github.gunkim.common

import io.ktor.server.auth.jwt.*
import java.util.*

val JWTPrincipal.userId: UUID
    get() = UUID.fromString(this.payload.getClaim("userId").asString())
