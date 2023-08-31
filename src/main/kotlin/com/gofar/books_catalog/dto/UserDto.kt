package com.gofar.books_catalog.dto

data class UserDto(
    val username: String,
    val email: String,
    val password: String,
    val age: Int = 18
)
