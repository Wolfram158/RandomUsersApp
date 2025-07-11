package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
