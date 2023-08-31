package com.gofar.books_catalog.controllers

import com.gofar.books_catalog.dto.AuthorRequestDto
import com.gofar.books_catalog.dto.AuthorResponseDto
import com.gofar.books_catalog.exceptions.AuthorException
import com.gofar.books_catalog.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/authors")
class AuthorController(
    @Autowired private val authorService: AuthorService
) {

    @PostMapping
    fun saveAuthor(@RequestBody authorRequestDto: AuthorRequestDto): ResponseEntity<String> {
        this.authorService.saveAuthor(authorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
    }

    @GetMapping("/id/{id}")
    fun getAuthorById(@PathVariable(name = "id") id: Long): ResponseEntity<Any> {
        val authorResponseDto: AuthorResponseDto;
        try {
            authorResponseDto = this.authorService.getAuthor(id);
        }catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message);
        }

        return ResponseEntity.status(HttpStatus.OK).body(authorResponseDto);
    }

    @GetMapping("/name/{name}")
    fun getAuthorByName(@PathVariable(name = "name") name: String): ResponseEntity<Any> {
        val authorResponseDto: AuthorResponseDto;
        try {
            authorResponseDto = this.authorService.getAuthor(name);
        }catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(authorResponseDto);
    }

    @GetMapping
    fun getAllAuthors(): ResponseEntity<List<AuthorResponseDto>> {
        val authors = this.authorService.getAuthors();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable(name = "id") id: Long, @RequestBody request: AuthorRequestDto): ResponseEntity<String> {
        try {
            this.authorService.updateAuthor(id, request);
        }catch (e: AuthorException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Author with id ${id} updated successfully");
    }
}