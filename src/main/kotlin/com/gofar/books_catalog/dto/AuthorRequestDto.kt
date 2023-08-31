package com.gofar.books_catalog.dto

data class AuthorRequestDto(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val dateOfDeath: String? = "",
    val biography: String
)