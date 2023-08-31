package com.gofar.books_catalog.dto

data class BookResponseDto(
    val id: Long,
    val isbn: String,
    val title: String,
    val publishYear: String,
    val creationDate: String,
    val lastUpdate: String?,
    val genre: String,
)