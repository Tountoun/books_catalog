package com.gofar.books_catalog.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@JsonComponent
class LocalDateSerializer: JsonSerializer<LocalDate>() {
    override fun serialize(value: LocalDate?, generator: JsonGenerator?, serializer: SerializerProvider?) {
        if (value != null) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            generator?.writeString(formatter.format(value))
        }
    }
}