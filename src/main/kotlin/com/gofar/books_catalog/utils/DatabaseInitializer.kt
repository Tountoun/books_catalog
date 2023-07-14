package com.gofar.books_catalog.utils

import com.gofar.books_catalog.models.User
import com.gofar.books_catalog.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer: CommandLineRunner {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun run(vararg args: String?) {
/*
        val admin = User(
            id = 100,
            username = "tilltates",
            email = "tilltates8@gmail.com",
            password = passwordEncoder.encode("tilltates12"),
            roles = setOf(Role.ADMIN),
            age = 20
        )
        val user = User(
            id = 20,
            username = "mikeaya",
            email = "mike@gmail.com",
            password = passwordEncoder.encode("mikeaya12"),
            roles = setOf(Role.USER),
            age = 14
        )
        val all = listOf<User>(admin, user)

        userRepository.saveAll(all)
*/
    }

}