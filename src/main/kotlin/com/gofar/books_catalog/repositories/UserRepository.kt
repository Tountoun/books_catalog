package com.gofar.books_catalog.repositories

import com.gofar.books_catalog.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String): Boolean

    fun findByEmail(email: String): User?

    fun findAllByUsername(username: String): List<User>

    fun findAllByAgeBetween(minAge: Int, maxAge: Int): List<User>

    fun deleteByEmail(email: String): Unit
}