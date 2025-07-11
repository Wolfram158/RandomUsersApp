package ru.nda.users.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: NameDto,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("email") val email: String,
    @SerializedName("login") val login: LoginDto,
    @SerializedName("dob") val dob: DobDto,
    @SerializedName("registered") val registered: RegisteredDto,
    @SerializedName("picture") val picture: PictureDto,
    @SerializedName("phone") val phone: String
)
