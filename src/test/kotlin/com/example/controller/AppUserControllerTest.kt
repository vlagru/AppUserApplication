package com.example.controller

import com.example.model.AppUser
import com.example.model.request.AppUserRequest
import io.micronaut.context.annotation.Requires
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
@Requires(env = ["integration-tests"])
class AppUserControllerTest {

    @Test
    fun getAllUsersTest(spec: RequestSpecification) {

        spec
            .`when`()
            .get("user/getAllUsers")
            .then()
            .statusCode(200)
            .header("Content-Type", "application/json")
            .body("size()", `is`(0))
    }

    @Test
    fun complexEndpointsTest(spec: RequestSpecification) {

        //POST create
        spec
            .`when`()
            .contentType(ContentType.JSON)
            .body(
                AppUserRequest(
                    "Petr Novak",
                    "petr.novak@gmail.com",
                    "Heyrovskeho",
                    "Prague",
                    25801,
                )
            )
            .post("user/createUser")
            .then()
            .statusCode(201)

        //GET all
        val list = spec
            .`when`()
            .get("user/getAllUsers")
            .then()
            .statusCode(200)
            .body("size()", `is`(1))
            .extract()
            .`as`(object : TypeRef<List<AppUser>>() {})

        Assertions.assertEquals(1, list.size)
        val createUser = list.first()
        Assertions.assertEquals("Petr Novak", createUser.name)
        Assertions.assertEquals("Heyrovskeho", createUser.address.street)

        //GET by id
        spec
            .`when`()
            .get("user/${createUser.id}")
            .then()
            .statusCode(200)

        //DELETE by id
        spec
            .`when`()
            .delete("user/${createUser.id}")
            .then()
            .statusCode(204)
    }
}
