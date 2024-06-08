package com.example.controller

import com.example.model.Address
import com.example.model.AppUser
import com.example.model.request.AppUserRequest
import com.example.model.request.SearchRequest
import com.example.publisher.AppUserProducer
import com.example.service.AppUserService
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn


@Controller("/user")
class AppUserController(

    private val appUserService : AppUserService,
    private val appUserProducer: AppUserProducer

) {

    //TODO Check the class extension
    @Post("createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    fun create(@Body appUserRequest: AppUserRequest) =
        appUserService.create(
            appUser = toAppUser(appUserRequest)
        )

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

    @Get("/getAllUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Status(HttpStatus.OK)
    fun getAllUsers(): List<AppUser> = appUserService.getAll()

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Status(HttpStatus.OK)
    fun getUserById (@PathVariable id: String) =
        appUserService.getUserById(id)

    @Delete("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Status(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id : String) : String =
        appUserService.deleteUser(id)

    //TODO Finish mapping AppUser to response DTO
    //@Consumes and Produces are not necessary
    @Put("/{id}")
    @Status(HttpStatus.OK)
    fun updateUser(
        @PathVariable id: String,
        @Body appUserRequest: AppUserRequest,
        @Header("headerTest") header: String
    ): AppUser {
        return appUserService.updateUser(
            id, appUserRequest
        )
    }

    @Post("/search")
    fun search(@Body searchRequest: SearchRequest) : List<AppUser> =
        appUserService.search(searchRequest)

    @Post("/publishJsonMessage")
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Status(HttpStatus.OK)
    fun publishJsonMessage(@Body appUserRequest: AppUserRequest){
        appUserProducer.publish(appUserRequest)
    }
}
