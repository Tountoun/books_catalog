package com.gofar.books_catalog.repositories

import com.gofar.books_catalog.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AuthorRepository: JpaRepository<Author, Long> {

    fun findAuthorByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(firstName: String, lastName: String): Author?;

    fun findAuthorsByFirstNameAndLastNameContainingIgnoreCaseOrderByFirstName(firstName: String, lastName: String): List<Author>;

    fun findAuthorsByDateOfBirthBetween(start: LocalDate, end: LocalDate): List<Author>;
}