package com.gofar.books_catalog.services

import com.gofar.books_catalog.exceptions.GenreNotFoundException
import com.gofar.books_catalog.models.Book
import com.gofar.books_catalog.repositories.BookRepository
import com.gofar.books_catalog.utils.Genre
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository) {

    fun getAllBooks(): MutableList<Book> {
        return bookRepository.findAll()
    }

    fun getSingleBook(bookId: Long): Book? {
        if(bookRepository.existsById(bookId)) {
            return bookRepository.findById(bookId).get()
        }
        return null
    }

    fun createBook(book: Book): Int {
        if (bookRepository.existsByTitle(book.title))
            return -1
        return bookRepository.save(book).id.toInt()
    }

    fun createBooks(books: List<Book>): List<Book> {
        return bookRepository.saveAll(books).toList()
    }

    fun createBookList(books: List<Book>) {
        bookRepository.saveAll(books)
    }

    fun updateBook(bookId: Long, book: Book): Book? {
        if (bookRepository.existsById(bookId)) {
            return bookRepository.save(book)
        }
        return null
    }

    fun deleteBook(bookId: Long): Boolean {
        if (bookRepository.existsById(bookId))
            bookRepository.deleteById(bookId)
                .run { return true }
        return false
    }

    fun searchBook(keyword: String): List<Book> {
        val books = mutableListOf<Book>()
        val enumContainingKeyword = mutableSetOf<Genre>()

        for (genre: Genre in Genre.values()) {
            if (genre.name.contains(keyword.uppercase(Locale.getDefault())))
                enumContainingKeyword.add(genre)
        }

        bookRepository
            .findByTitleContainingOrAuthorContaining(keyword)
            .forEach { books.add(it) }
        enumContainingKeyword
            .forEach {
                bookRepository.findByGenre(it)
                    .forEach { book: Book ->
                        books.add(book)
                    }
            }
        return books.toList()
    }

    fun filter(genreValue: String, year: Int): List<Book> {
        var searchGenre: Genre? = null

        for (genre: Genre in Genre.values()) {
            if (genre.genre.equals(genreValue, ignoreCase = true)) {
                searchGenre = genre
            }
        }
        if (searchGenre == null) {
            throw GenreNotFoundException("Genre not found")
        }
        return bookRepository
                .findByGenreAndPublicationYear(searchGenre, year)

    }

    fun getBooksAtPageWithSize(page: Int, size: Int): List<Book> {
        val pageable: Pageable = PageRequest.of(page, size)
        val books = bookRepository.findAll(pageable)
        return books.content.toList()
    }
}