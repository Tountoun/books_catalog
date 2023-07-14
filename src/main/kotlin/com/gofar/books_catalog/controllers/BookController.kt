package com.gofar.books_catalog.controllers

import com.gofar.books_catalog.models.Book
import com.gofar.books_catalog.services.BookService
import com.gofar.books_catalog.utils.Message
import com.gofar.books_catalog.validators.BookValidator
import org.apache.commons.logging.Log
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
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
    private val bookValidator: BookValidator
    ) {

    @GetMapping
    fun getBooks(): HttpEntity<List<Book>?> {
        val books = bookService.getAllBooks()
        if (books.size != 0) {
            return ResponseEntity.ok(books)
        }
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{id}")
    fun getBook(@PathVariable(name = "id") bookId: Long): ResponseEntity<out Any> {
        val book = bookService.getSingleBook(bookId)
        if (book != null) {
            return ResponseEntity.ok(book)
        }
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Message("Book with id $bookId not found"))
    }

    @PostMapping
    fun createBook(@RequestBody book: Book, bindingResult: BindingResult): ResponseEntity<Message> {
        bookValidator.validate(book, bindingResult)

        if (bindingResult.hasErrors()) {
            var errorsMap = mutableMapOf<String, String>()

            for (error: FieldError in bindingResult.getFieldErrors()) {
                errorsMap.put(error.field, error.defaultMessage?: error.code.toString())
            }
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Message(errorsMap.toString()))
        }
        val bookReturn = bookService.createBook(book)
        if (bookReturn != -1)
            return ResponseEntity
                .ok(Message("Book created successfully"))
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message("Wrong request"))
    }

    @PostMapping("/list")
    fun createBooks(@RequestBody books: List<Book>): ResponseEntity<out Any> {
        val result = bookService.createBooks(books)
        if (books.isEmpty())
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Message("No book created"))
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result)
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable(name = "id") bookId: Long): ResponseEntity<Message> {
        if (bookService.deleteBook(bookId))
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Message("Book with id $bookId deleted successfully"))
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Message("Book with id $bookId not found"))
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable(name = "id", required = true) bookId: Long, @RequestBody book: Book): ResponseEntity<Message> {
        if (bookService.updateBook(bookId, book) != null) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Message("Bood with id $bookId updated successfully"))
        }
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Message("Book with id $bookId not found"))
    }

    @GetMapping("/search")
    fun searchBook(@RequestParam(name = "query", required = true) keyword: String): ResponseEntity<out Any> {
        val books = bookService.searchBook(keyword)

        if (books.size != 0)
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
        @RequestParam(name = "year") year: Int
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