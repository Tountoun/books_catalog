package com.gofar.books_catalog.dto

import com.gofar.books_catalog.utils.Genre

data class BookRequestDto(
    val isbn: String,
    val title: String,
    val publishYear: String,
    val genre: Genre,
    val author_id: Long
)