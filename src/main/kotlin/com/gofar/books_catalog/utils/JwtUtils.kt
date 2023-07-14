package com.gofar.books_catalog.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.collections.HashMap

class JwtUtils {

    fun generateAccessToken(userDetails: UserDetails): String {
        val claims = hashMapOf<String, Any>()
        return createToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION)
    }

    fun generateAccessToken(userDetails: UserDetails, claims: HashMap<String, Any>): String {
        return createToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION)
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        val claims = hashMapOf<String, Any>()
        return createToken(claims, userDetails, REFRESH_TOKEN_EXPIRATION)
    }

    private fun createToken(claims: HashMap<String, Any>, userDetails: UserDetails, expiration: Long): String {

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .claim("authorities", userDetails.authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, Companion.SECRET_KEY)
            .compact()
    }
    fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .body
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

     fun extractUsername(token: String): String {
        return extractClaims(token).subject
    }

    fun isTokenExpired(token: String): Boolean {
        return extractClaims(token).expiration.before(Date())
    }
    companion object {
        private val SECRET_KEY = "LKNABUIFJUFALJBLBGLABF426268972PIMHK/BQKF/VLKBLQFBAF"
        private val ACCESS_TOKEN_EXPIRATION: Long = 3600000
        private val REFRESH_TOKEN_EXPIRATION: Long = 86400000
    }
}