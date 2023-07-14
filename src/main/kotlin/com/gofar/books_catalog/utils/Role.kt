package com.gofar.books_catalog.utils

import com.fasterxml.jackson.annotation.JsonCreator

enum class Role(val role: String) {
    ADMIN("ADMIN"),
    USER("USER");

    companion object {
        @JvmStatic
        @JsonCreator
        fun getRoleFromString(role: String): Role? {
            for (r in Role.values()) {
                if (r.name.equals(role, ignoreCase = true))
                    return r
            }
            return null
        }
    }
}
