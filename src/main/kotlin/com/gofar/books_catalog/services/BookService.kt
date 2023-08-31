package com.gofar.books_catalog.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.gofar.books_catalog.dto.BookRequestDto
import com.gofar.books_catalog.dto.BookResponseDto
import com.gofar.books_catalog.exceptions.BookException
import com.gofar.books_catalog.exceptions.GenreNotFoundException
import com.gofar.books_catalog.models.Book
import com.gofar.books_catalog.repositories.AuthorRepository
import com.gofar.books_catalog.repositories.BookRepository
import com.gofar.books_catalog.utils.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository, private val authorRepository: AuthorRepository) {
    @Autowired
    private lateinit var mapper: ObjectMapper;

    fun getAllBooks(): MutableList<BookResponseDto> {
        val response = mutableListOf<BookResponseDto>();
        bookRepository.findAll().forEach { book: Book? ->
            response.add(mapper.convertValue(book, BookResponseDto::class.java)) };
        return response;
    }

    fun getSingleBook(bookId: Long): BookResponseDto {
        val book = bookRepository.findById(bookId).orElseThrow { BookException("Book with id ${bookId} not found") };
        return mapper.convertValue(book, BookResponseDto::class.java);
    }
/*

    fun getBookWithAuthor(bookId: Long): BookResponseDto? {
        if (bookRepository.existsById(bookId)) {
            val book = bookRepository.findBookWithAuthor(bookId);
            val dto = mapper.convertValue(book, BookResponseDto::class.java);
            return dto;
        }
        return null;
    }
*/

    fun createBook(bookRequest: BookRequestDto): BookResponseDto {
        if (bookRepository.existsByIsbn(bookRequest.isbn))
            throw BookException("Book with isbn number ${bookRequest.isbn} already exists");
        val author = authorRepository.findById(bookRequest.author_id).orElseThrow{
            throw BookException("Author with id ${bookRequest.author_id} not found") };

        val book = Book(bookRequest.isbn, bookRequest.title,author, bookRequest.publishYear, bookRequest.genre);
        bookRepository.save(book);
        return mapper.convertValue(book, BookResponseDto::class.java);
    }

    fun updateBook(bookId: Long, bookRequest: BookRequestDto) {
        val book = bookRepository.findByIsbn(bookRequest.isbn).orElseThrow {
            BookException("Book with isbn number ${bookRequest.isbn} already exists") }
        val author = authorRepository.findById(bookRequest.author_id).orElseThrow{
            BookException("Author with id ${bookRequest.author_id} not found") };
        book.title = bookRequest.title;
        book.author = author;
        book.genre = bookRequest.genre;
        book.publishYear = bookRequest.publishYear;
        bookRepository.saveAndFlush(book);
    }

    fun deleteBook(bookId: Long) {
        if (bookRepository.existsById(bookId))
            bookRepository.deleteById(bookId);
        else
            throw BookException("Book with id ${bookId} not found");
    }

    fun searchBook(keyword: String): List<Book> {
        val books = mutableListOf<Book>()
        val enumContainingKeyword = mutableSetOf<Genre>()

        for (genre: Genre in Genre.values()) {
            if (genre.name.contains(keyword.uppercase(Locale.getDefault())))
                enumContainingKeyword.add(genre)
        }

        bookRepository
            .findByTitleContaining(keyword)
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

    fun filter(genreValue: String, year: String): List<BookResponseDto> {
        var searchGenre: Genre? = null

        for (genre: Genre in Genre.values()) {
            if (genre.genre.equals(genreValue, ignoreCase = true)) {
                searchGenre = genre
            }
        }
        if (searchGenre == null) {
            throw GenreNotFoundException("Genre ${genreValue} not found")
        }
        return (bookRepository
                .findByGenreAndPublishYear(searchGenre, year).map {
                    book -> mapper.convertValue(book, BookResponseDto::class.java) });

    }

    fun getBooksAtPageWithSize(page: Int, size: Int): List<Book> {
        val pageable: Pageable = PageRequest.of(page, size)
        val books = bookRepository.findAll(pageable)
        return books.content.toList()
    }
}