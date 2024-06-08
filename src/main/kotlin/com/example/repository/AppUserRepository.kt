package com.example.repository

import com.example.model.AppUser
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface AppUserRepository: CrudRepository<AppUser, String>{

    /**
     * finder method
     * @param email
     */
    fun findByEmailEquals(email: String): List<AppUser>


    /**
     * finder method - finds all users whose name contains the parameter name
     * the query can be tested in, for instance, MongoDB Compass application
     * @param name
     */
    @MongoFindQuery("{name:{\$regex: :name, \$options: 'i'}}")
    fun findByNameLike(name: String): List<AppUser>

}
