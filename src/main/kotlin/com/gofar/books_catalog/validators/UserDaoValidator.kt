package com.gofar.books_catalog.validators

import com.gofar.books_catalog.models.User
import com.gofar.books_catalog.utils.UserDao
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserDaoValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return UserDao::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "Username is required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required",  "Password is required")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email is required")

        val userDao = target as UserDao

        if (userDao.username.length < 4) {
            errors.rejectValue("username", "username.minlength", "Username must have at least 6 characters")
        }
        if (userDao.password.length < 8) {
            errors.rejectValue("password", "password.minlenght", "Password must have at least eight characters")
        }

        val res = userDao.password.any{ it.isDigit() }

        /*
        if (userDao.password.any{ it.isDigit() } == true) {
            errors.rejectValue("password", "password.onedigitatleast", "Pasword must contains at least one digit")
        }

         */
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (!userDao.email.matches(emailRegex.toRegex())) {
            errors.rejectValue("email", "email.invalid", "Invalid email")
        }
    }
}