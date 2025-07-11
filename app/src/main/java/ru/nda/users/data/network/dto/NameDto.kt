package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class NameDto(
    @SerializedName("first") val firstName: String,
    @SerializedName("last") val lastName: String,
)
