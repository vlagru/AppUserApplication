package com.example.service

import com.example.model.Address
import com.example.model.AppUser
import com.example.model.request.AppUserRequest
import com.example.model.request.SearchRequest
import com.example.repository.AppUserRepository
import jakarta.inject.Singleton

@Singleton
class AppUserService (
    private val appUserRepository: AppUserRepository
) {
    fun create(appUser: AppUser): AppUser =
        appUserRepository.save(appUser)

    fun getAll(): List<AppUser> =
        appUserRepository.findAll()

    //TODO Study and finish throwing an exception regarding the source not found
    fun getUserById(id: String): AppUser =
        appUserRepository.findById(id).get()

    fun deleteUser(id: String): String {
        appUserRepository.deleteById(id)
        return "User with id $id has been deleted"
    }

    fun updateUser(id: String, appUserRequest: AppUserRequest): AppUser {

        val foundUser = getUserById(id)

        val appUser = AppUser(
            id = foundUser.id,
            name = appUserRequest.name,
            email = appUserRequest.email,
            address = Address(
                street = appUserRequest.street,
                city = appUserRequest.city,
                code = appUserRequest.code
            )
        )

        return appUserRepository.update(appUser)
    }

    fun search(searchRequest: SearchRequest): List<AppUser> =
        when {
            searchRequest.email != null -> appUserRepository.findByEmailEquals(searchRequest.email)
            searchRequest.name != null -> appUserRepository.findByNameLike(searchRequest.name)
            else -> emptyList()
        }
}
