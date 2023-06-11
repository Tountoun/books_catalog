package com.gofar.books_catalog.repositories

import com.gofar.books_catalog.models.Book
import com.gofar.books_catalog.utils.Genre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long> {

    fun existsByTitle(title: String): Boolean

    @Query(
        "SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%"
    )
    fun findByTitleContainingOrAuthorContaining(@Param("keyword") keyword: String): List<Book>

    fun findByGenre(keyword: Genre): List<Book>

    fun findByGenreAndPublicationYear(genre: Genre, publicationYear: Int): List<Book>
}