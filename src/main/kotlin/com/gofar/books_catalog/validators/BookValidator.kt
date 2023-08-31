package com.gofar.books_catalog.validators

import com.gofar.books_catalog.models.Book
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class BookValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return Book::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "author.required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "genre", "genre.required")

        val book = target as Book

    }
}