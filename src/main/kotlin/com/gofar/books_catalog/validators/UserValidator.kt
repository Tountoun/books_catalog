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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "Username required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email required")
        val user = target as User

        if (user.username.length < 6) {
            errors.rejectValue("username", "username.minlength", "Username must have at least 6 characters")
        }
        if (user.password.length < 8) {
            errors.rejectValue("password", "password.minlenght", "Password must have at least eight characters")
        }
        if (user.password.any{ !it.isDigit() }) {
            errors.rejectValue("password", "password.onedigitatleast", "Password must contains at least one digit")
        }
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (!user.email.matches(emailRegex.toRegex())) {
            errors.rejectValue("email", "email.invalid", "Invalid email")
        }
    }
}