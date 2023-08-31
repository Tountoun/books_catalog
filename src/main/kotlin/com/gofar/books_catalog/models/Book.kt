package com.gofar.books_catalog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gofar.books_catalog.utils.Genre
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
@NamedEntityGraph(name="Book.author", attributeNodes = [NamedAttributeNode(value = "author")])
class Book {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    var id: Long = 0;
    @Column(name = "isbn", unique = true)
    var isbn: String = "";
    @NotNull
    @Column(unique = false)
    var title: String = "";
    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id")
    var author: Author = Author();
    @Column(length = 4)
    var publishYear: String = "";
    @Column(name="created_at")
    var creationDate: LocalDateTime? = null;
    @Column(name="last_updated")
    var lastUpdate: LocalDateTime? = null;
    @NotNull
    @Enumerated(EnumType.STRING)
    var genre: Genre = Genre.AUTOBIOGRAPHY;

    constructor(){}

    constructor(isbn: String, title: String, author: Author, publishYear: String, genre: Genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.genre = genre;
    }

    @PrePersist
    fun prePersist() {
        creationDate = LocalDateTime.now();
        lastUpdate = creationDate;
    }

    @PreUpdate
    fun preUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
