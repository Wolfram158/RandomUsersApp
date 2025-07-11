package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("street") val streetDto: StreetDto,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("coordinates") val coordinatesDto: CoordinatesDto,
    @SerializedName("timezone") val timezoneDto: TimezoneDto
)
