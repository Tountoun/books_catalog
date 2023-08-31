package com.gofar.books_catalog.controllers

import com.gofar.books_catalog.services.UserService
import com.gofar.books_catalog.utils.Message
import com.gofar.books_catalog.utils.Role
import com.gofar.books_catalog.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UserController(
    @Autowired private val userService: UserService
) {

    @PostMapping("/register_user")
    fun registerUser(@RequestBody userDao: UserDto, bindingResult: BindingResult): ResponseEntity<out Any> {
        val user = userService.register(userDao, bindingResult, Role.USER)
        if (user != null) {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user)
        }
        println("Hop")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message("Can't be the new user"))
    }

    @PostMapping("/register_admin")
    fun registerAdmin(@RequestBody userDao: UserDto, bindingResult: BindingResult): ResponseEntity<out Any> {
        val user = userService.register(userDao, bindingResult, Role.ADMIN)
        if (user != null) {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user)
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message("Can't be the new user"))
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable(name = "email", required = true) email: String): ResponseEntity<out Any> {
        val user = userService.getUserByEmail(email)
        if (user != null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(user)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message("User with email $email not found"))
    }
}