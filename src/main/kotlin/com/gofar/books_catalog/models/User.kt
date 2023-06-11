package com.gofar.books_catalog.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,)
    val id: Long,
    @Column(name = "username")
    val username: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "age")
    val age: Int
    )