package com.gofar.books_catalog.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

//@Entity
data class RefreshToken (
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Id
    val id: Long = 0,
    //@Column(name = "token")
    val refreshValue: String
) {
    constructor(): this(0, "")
}