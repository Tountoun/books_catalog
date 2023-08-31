package com.gofar.books_catalog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDate
import kotlin.jvm.Transient

@Entity
@Table(name = "author")
class Author() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0;
    @Column(length = 30, nullable = false)
    var firstName: String = "";
    @Column(length = 30, nullable = false)
    var lastName: String = "";
    @Column(columnDefinition = "Date", nullable = false)
    var dateOfBirth: LocalDate = LocalDate.now();
    @Column(columnDefinition = "Date", nullable = true)
    var dateOfDeath: LocalDate? = null;
    @Column(columnDefinition = "TEXT", nullable = false)
    var biography: String = "";
    @Transient
    var books: MutableSet<Book> = mutableSetOf();

    fun addBook(book: Book) {
        this.books.add(book);
    }

    fun removeBook(book: Book) {
        this.books.remove(book);
    }
}