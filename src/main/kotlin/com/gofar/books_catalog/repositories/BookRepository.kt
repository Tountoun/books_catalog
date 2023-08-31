package com.gofar.books_catalog.repositories

import com.gofar.books_catalog.models.Book
import com.gofar.books_catalog.utils.Genre
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BookRepository: JpaRepository<Book, Long> {

    @EntityGraph(value = "Book.author")
    @Query("select b from Book b where b.id = :id")
    fun findBookWithAuthor(@Param("id") id: Long): Book;

    fun findByIsbn(isbn: String): Optional<Book>
    fun existsByTitle(title: String): Boolean

    @Query(
        "SELECT b FROM Book b WHERE b.title LIKE %:keyword%"
    )
    fun findByTitleContaining(@Param("keyword") keyword: String): List<Book>

    fun findByGenre(keyword: Genre): List<Book>

    fun findByGenreAndPublishYear(genre: Genre, publicationYear: String): List<Book>

    fun findBooksByGenreIn(genres: List<Genre>);

    @Query(
        "FROM Book b WHERE b.author.id=:author_id"
    )
    fun findBooksByAuthorId(@Param("author_id") id: Long): List<Book>;

    fun existsByIsbn(isbn: String): Boolean;
}