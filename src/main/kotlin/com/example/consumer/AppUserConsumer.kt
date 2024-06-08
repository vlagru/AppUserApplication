package com.example.consumer

import com.example.model.Address
import com.example.model.AppUser
import com.example.model.request.AppUserRequest
import com.example.service.AppUserService
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener

@RabbitListener
class AppUserConsumer(

    private val appUserService: AppUserService

) {

    @Queue("queueAppUserTest")
    fun consume(appUserRequest: AppUserRequest){
        appUserService.create(appUser = toAppUser(appUserRequest))
    }

    private fun toAppUser(appUserRequest: AppUserRequest): AppUser =
        AppUser(
            name = appUserRequest.name,
            email = appUserRequest.email,
            address = Address(
                appUserRequest.street,
                appUserRequest.city,
                appUserRequest.code
            )
        )
}
