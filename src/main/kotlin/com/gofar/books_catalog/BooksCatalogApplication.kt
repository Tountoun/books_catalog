package com.gofar.books_catalog

import com.gofar.books_catalog.validators.BookValidator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooksCatalogApplication

fun main(args: Array<String>) {
	runApplication<BooksCatalogApplication>(*args)
}

fun bookValidator(): BookValidator {
	return BookValidator()
}