package com.gofar.books_catalog.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.gofar.books_catalog.dto.AuthorRequestDto
import com.gofar.books_catalog.dto.AuthorResponseDto
import com.gofar.books_catalog.exceptions.AuthorException
import com.gofar.books_catalog.models.Author
import com.gofar.books_catalog.repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(@Autowired private val authorRepository: AuthorRepository) {
    @Autowired
    private lateinit var mapper: ObjectMapper;

    fun saveAuthor(authorRequestDto: AuthorRequestDto): AuthorResponseDto {
        val author = mapper.convertValue(authorRequestDto, Author::class.java);
        this.authorRepository.save(author);
        val responseDto = mapper.convertValue(author, AuthorResponseDto::class.java);
        return responseDto;
    }

    fun getAuthors(): List<AuthorResponseDto> {
        val authors: MutableList<Author> = this.authorRepository.findAll();
        return (authors.map {
            mapper.convertValue(it, AuthorResponseDto::class.java)
        });
    }

    fun getAuthor(name: String): AuthorResponseDto {
        val author = this.authorRepository.findAuthorByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(name, name)
            ?: throw AuthorException("Author with first name or last name like ${name} not found");
        val responseDto = mapper.convertValue(author, AuthorResponseDto::class.java);
        return responseDto;
    }

    fun getAuthor(id: Long): AuthorResponseDto {
        val author = this.authorRepository.findById(id).orElseThrow { AuthorException("Author with id ${id} not found")};
        val authorResponseDto = mapper.convertValue(author, AuthorResponseDto::class.java);
        return authorResponseDto;
    }

    fun updateAuthor(id: Long, authorRequestDto: AuthorRequestDto) {
        val author = this.authorRepository.findById(id).orElseThrow { AuthorException("Author with id ${id} not found") }
        author.firstName = authorRequestDto.firstName;
        author.lastName = authorRequestDto.lastName;
        author.biography = authorRequestDto.biography;
        author.dateOfDeath = LocalDate.parse(authorRequestDto.dateOfDeath);
        this.authorRepository.saveAndFlush(author);
    }
}