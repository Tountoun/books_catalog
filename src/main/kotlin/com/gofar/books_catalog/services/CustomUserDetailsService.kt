package com.gofar.books_catalog.services

import com.gofar.books_catalog.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Collections

@Service
@RequiredArgsConstructor
class CustomUserDetailsService(
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val userRepository: UserRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {

        val user = userRepository.findByEmail(email)?: throw UsernameNotFoundException("Not found")

        val userDetails: UserDetails = User(
            user.email,
            user.password,
            Collections.singleton(SimpleGrantedAuthority(user.roles.first().role))
        )

        return userDetails
    }
}