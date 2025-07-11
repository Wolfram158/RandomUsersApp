package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class TimezoneDto(
    @SerializedName("offset") val offset: String,
    @SerializedName("description") val description: String
)
