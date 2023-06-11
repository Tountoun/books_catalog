package com.gofar.books_catalog.utils

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

enum class Genre(genre: String) {

    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    FICTION("Fiction"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    HISTORICAL_FICTION("Historical Fiction"),
    HORROR("Horror"),
    BIOGRAPHY("Biography"),
    AUTOBIOGRAPHY("Autobiography"),
    NON_FICTION("Non Fiction"),
    ADVENTURE("Adventure"),
    YOUNG_ADULT("Young Adult");

    var genre: String

    init {
        this.genre = genre.uppercase(Locale.getDefault())
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun getGenreFromString(str: String): Genre? {
            for (gen: Genre in Genre.values()) {
                if (gen.genre.equals(str, ignoreCase = true))
                    return gen
            }
            return null
        }
    }
}