package ru.nda.users.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.nda.users.data.network.dto.UsersResultDto

interface ApiService {
    @GET("/api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String,
    ): UsersResultDto
}