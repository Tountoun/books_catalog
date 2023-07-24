package com.gofar.books_catalog.dao

import com.gofar.books_catalog.utils.Genre

data class BookDao(
    val title: String?,
    val author: String?,
    val publicationYear: Int?,
    val genre: Genre?
)