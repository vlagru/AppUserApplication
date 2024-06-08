package com.example.model.request

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Deserializable
data class SearchRequest(
    val name: String? = null,
    val email: String? = null
)
