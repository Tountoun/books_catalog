package com.gofar.books_catalog.dto

data class AuthorResponseDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val dateOfDeath: String? = "",
    val biography: String
)