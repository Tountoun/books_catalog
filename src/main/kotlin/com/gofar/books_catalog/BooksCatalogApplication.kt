package com.gofar.books_catalog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.gofar.books_catalog.validators.BookValidator
import com.gofar.books_catalog.validators.UserDaoValidator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BooksCatalogApplication

fun main(args: Array<String>) {
	runApplication<BooksCatalogApplication>(*args)
}

@Bean
fun bookValidator(): BookValidator {
	return BookValidator()
}

@Bean
fun userDaoValidator(): UserDaoValidator {
	return UserDaoValidator()
}

@Bean
fun objectMapper(): ObjectMapper {
	val objectMapper = ObjectMapper();
	objectMapper.registerModule(JavaTimeModule());
	return objectMapper;
}

