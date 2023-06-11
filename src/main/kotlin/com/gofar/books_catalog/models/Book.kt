package com.gofar.books_catalog.models

import com.gofar.books_catalog.utils.Genre
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.Date

@Entity
data class Book(
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO,
    )
    @Column(name = "book_id")
    val id: Long,
    @NotNull
    @Column(unique = true)
    val title: String,
    val author: String = "Unknown",
    val publicationYear: Int,
    @NotNull
    @Enumerated(EnumType.STRING)
    val genre: Genre
)
