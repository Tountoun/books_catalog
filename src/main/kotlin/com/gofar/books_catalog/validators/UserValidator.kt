package com.gofar.books_catalog.validators

import com.gofar.books_catalog.models.User
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

class UserValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return User::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required")

        val user = target as User

        if (user.username.length < 6) {
            errors.rejectValue("username", "username.minlength")
        }
        // The password contains at least one digit
        if (user.password.any{ !it.isDigit() }) {
            errors.rejectValue("password", "password.onedigitatleast")
        }

    }
}