package com.gofar.books_catalog.controllers

import com.gofar.books_catalog.models.RefreshToken
import com.gofar.books_catalog.repositories.RefreshTokenRepository
import com.gofar.books_catalog.repositories.UserRepository
import com.gofar.books_catalog.utils.AuthDao
import com.gofar.books_catalog.services.CustomUserDetailsService
import com.gofar.books_catalog.utils.JwtUtils
import com.gofar.books_catalog.utils.LoginResponse
import com.gofar.books_catalog.utils.Message
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.acos

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    @Autowired private val refreshTokenRepository: RefreshTokenRepository
) {

    private val jwtUtils = JwtUtils()

    @PostMapping("/access_token")
    fun authenticate(
        @RequestBody authDao: AuthDao
    ): ResponseEntity<LoginResponse> {
        val authentication = UsernamePasswordAuthenticationToken(
            authDao.email,
            authDao.password
        )
        authenticationManager.authenticate(authentication)

        val userDetails = customUserDetailsService.loadUserByUsername(authDao.email)
        val accessToken = jwtUtils.generateAccessToken(userDetails)
        val refreshToken = jwtUtils.generateRefreshToken(userDetails)

        refreshTokenRepository.save(RefreshToken(refreshValue = refreshToken))

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(LoginResponse(accessToken, refreshToken))
    }

    @PostMapping("/refresh_token")
    fun refreshToken(@RequestBody refresh: String): ResponseEntity<out Any> {
        if (refreshTokenRepository.existsByRefreshValue(refresh)) {
            val refreshToken = refreshTokenRepository.findByRefreshValue(refresh).refreshValue
            if (jwtUtils.isTokenExpired(refreshToken)) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Message("Refresh token expired"))
            }
            val email = jwtUtils.extractUsername(refreshToken)
            val userDetails = customUserDetailsService.loadUserByUsername(email)
            val accessToken = jwtUtils.generateAccessToken(userDetails)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapOf("access_token" to accessToken))
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message("Invalid refresh token"))
    }
}