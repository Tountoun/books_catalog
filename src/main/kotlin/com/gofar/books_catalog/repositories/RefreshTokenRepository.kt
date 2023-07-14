package com.gofar.books_catalog.repositories

import com.gofar.books_catalog.models.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {

    fun existsByRefreshValue(refreshValue: String): Boolean

    fun findByRefreshValue(refreshValue: String): RefreshToken
}