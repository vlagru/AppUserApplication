package com.example.publisher

import com.example.model.request.AppUserRequest
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("exchangeAppUserTest")
interface AppUserProducer {

    @Binding("keyAppUserTest")
    fun publish(appUserRequest: AppUserRequest)
}
