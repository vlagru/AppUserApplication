package com.example.service

import com.example.model.Address
import com.example.model.AppUser
import com.example.repository.AppUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppUserServiceTest{
    private val appUserRepository = mockk<AppUserRepository>()

    private val appUserService = AppUserService(appUserRepository)

    @Test
    fun getAllTest(){
        every {
            appUserRepository.findAll()
        } returns arrayListOf(AppUser(
            "123456789123456789123456",
            "Petra Novotna",
            "petra.novotna@seznam.cz",
            Address(
                "Dablicka",
                "Prague",
                19501)
        ))

        val result = appUserService.getAll()

        assertTrue(result.isNotEmpty())

        verify(exactly = 1) {
            appUserRepository.findAll()
        }
    }
}
