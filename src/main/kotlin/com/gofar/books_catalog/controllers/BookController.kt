package com.gofar.books_catalog.controllers

import com.gofar.books_catalog.dto.BookRequestDto
import com.gofar.books_catalog.dto.BookResponseDto
import com.gofar.books_catalog.exceptions.BookException
import com.gofar.books_catalog.services.AuthorService
import com.gofar.books_catalog.services.BookService
import com.gofar.books_catalog.utils.Message
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/books")
class BookController(
    private val bookService: BookService,
    ) {

    @GetMapping
    fun getBooks(): ResponseEntity<List<BookResponseDto>> {
        val books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    fun getBook(@PathVariable(name = "id") bookId: Long): ResponseEntity<out Any> {
        return try {
            val response = bookService.getSingleBook(bookId);
            ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (e: BookException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message);
        }
    }

    @PostMapping
    fun createBook(@RequestBody bookRequestDto: BookRequestDto): ResponseEntity<out Any> {
        return try {
            val response = bookService.createBook(bookRequestDto);
            ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (e: BookException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message);
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable(name = "id") bookId: Long): ResponseEntity<String> {
        return try {
            bookService.deleteBook(bookId);
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Book with id ${bookId} deleted successfully");
        } catch (e: BookException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message);
        }
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable(name = "id", required = true) bookId: Long, @RequestBody bookRequestDto: BookRequestDto): ResponseEntity<String> {
        try {
            bookService.updateBook(bookId, bookRequestDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Book with id ${bookId} updated successfully");
        } catch (e: BookException) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(e.message);
        }
    }

    @GetMapping("/search")
    fun searchBook(@RequestParam(name = "query", required = true) keyword: String): ResponseEntity<out Any> {
        val books = bookService.searchBook(keyword)

        if (books.isNotEmpty())
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(books)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Message("No result"))
    }

    @GetMapping("/filter")
    fun filterByGenreAndYear(
        @RequestParam(name = "genre") genre: String,
        @RequestParam(name = "year") year: String
    ): ResponseEntity<out Any> {
        try {
            val books = bookService.filter(genre, year)

            if (books.size != 0)
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(books)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Message("No result"))

        }catch (e: Exception) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.message)
        }
    }

    @GetMapping("/pages")
    fun getPageWithSize(
        @RequestParam(name = "page", defaultValue = "1") page: Int,
        @RequestParam(name = "size", defaultValue = "1900") size: Int
    ): ResponseEntity<out Any> {
        val books = bookService.getBooksAtPageWithSize(page, size)

        if (books.size != 0)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(books)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Message("No result"))
    }
}