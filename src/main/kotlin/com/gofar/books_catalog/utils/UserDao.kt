package com.gofar.books_catalog.utils

data class UserDao(
    val username: String,
    val email: String,
    val password: String,
    val age: Int = 18
)
