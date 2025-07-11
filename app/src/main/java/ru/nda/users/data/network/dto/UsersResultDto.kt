package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class UsersResultDto(
    @SerializedName("results") val results: List<UserDto>
)
