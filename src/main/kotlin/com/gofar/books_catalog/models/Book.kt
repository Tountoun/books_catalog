package com.gofar.books_catalog.models

import com.gofar.books_catalog.utils.Genre
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull


@Entity
data class Book(
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO,
    )
    @Column(name = "book_id")
    var id: Long = 0,
    @NotNull
    @Column(unique = true)
    var title: String,
    var author: String = "Unknown",
    var publicationYear: Int,
    @NotNull
    @Enumerated(EnumType.STRING)
    var genre: Genre
) {
    constructor(): this(0, "", "", 0, Genre.AUTOBIOGRAPHY)
}
