package io.github.gunkim.domain

interface UserRepository {
    fun save(user: User): User
    fun findByName(name: String): User?
}
