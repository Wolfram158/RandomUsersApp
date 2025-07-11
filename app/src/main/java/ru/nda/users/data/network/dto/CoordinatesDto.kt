package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)
