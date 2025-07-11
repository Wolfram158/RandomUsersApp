package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class DobDto(
    @SerializedName("date") val date: String,
    @SerializedName("age") val age: String
)
