package com.gofar.books_catalog.models

import com.gofar.books_catalog.utils.Role
import jakarta.persistence.*
import lombok.Builder

@Entity
@Builder
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,)
    var id: Long = 0,
    @Column(name = "username")
    var username: String,
    @Column(name = "email", unique = true, nullable = false)
    var email: String,
    @Column(name = "password")
    var password: String,
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role>,
    @Column(name = "age")
    var age: Int
    ) {
    constructor(): this(0,"", "", "", mutableSetOf(), 0)
}