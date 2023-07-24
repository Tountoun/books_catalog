package com.gofar.books_catalog.services

import com.gofar.books_catalog.models.User
import com.gofar.books_catalog.repositories.UserRepository
import com.gofar.books_catalog.utils.Role
import com.gofar.books_catalog.dao.UserDao
import com.gofar.books_catalog.validators.UserDaoValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userDaoValidator: UserDaoValidator
) {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun register(userDao: UserDao, bindingResult: BindingResult, role: Role): User? {
        userDaoValidator.validate(userDao, bindingResult)

        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors
            for (error in errors) {
                println(error.defaultMessage)
            }
            return null
        }
        val user = User(
            username = userDao.username,
            password = passwordEncoder.encode(userDao.password),
            email = userDao.email,
            age = userDao.age,
            roles = mutableSetOf(role)
        )
        return userRepository.save(user)
    }


    fun getUserByEmail(email: String): User? {
        var user: User? = null
        if (userRepository.existsByEmail(email))
            user = userRepository.findByEmail(email)
        return user
    }

    fun getUsersByUsername(username: String): List<User> {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findAllByUsername(username)
        }
        return listOf()
    }

    fun getUsersWithAgeBetweenMinAndMax(minAge: Int, maxAge: Int): List<User> {
        return userRepository.findAllByAgeBetween(minAge, maxAge)
    }

    fun getUserWithAge(age: Int): User? {
        val users = getUsersWithAgeBetweenMinAndMax(age, age)
        return if (users.isEmpty()) null else users.first()
    }

    fun updateUser(id: Long, userDao: UserDao, bindingResult: BindingResult): User? {
        userDaoValidator.validate(userDao, bindingResult)

        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors
            for (error in errors) {
                println(error.defaultMessage)
            }
            return null
        }
        if (userRepository.existsById(id)) {
            val user = userRepository.findById(id).get()
            val newUser = User(
                user.id,
                userDao.username,
                userDao.email,
                userDao.password,
                user.roles,
                userDao.age)
            return userRepository.save(newUser)
        }
        return null
    }

    fun deleteUserByEmail(email: String): Boolean {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email)
            return true
        }
        return false
    }

}
