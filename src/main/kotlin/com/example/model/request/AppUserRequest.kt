package com.example.model.request

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AppUserRequest(
    val name: String,
    val email: String,
    val street: String,
    val city: String,
    val code: Int
)
