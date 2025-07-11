package ru.nda.users.data.mapper

import ru.nda.users.data.network.dto.CoordinatesDto
import ru.nda.users.data.network.dto.DobDto
import ru.nda.users.data.network.dto.LocationDto
import ru.nda.users.data.network.dto.LoginDto
import ru.nda.users.data.network.dto.NameDto
import ru.nda.users.data.network.dto.PictureDto
import ru.nda.users.data.network.dto.RegisteredDto
import ru.nda.users.data.network.dto.StreetDto
import ru.nda.users.data.network.dto.TimezoneDto
import ru.nda.users.data.network.dto.UserDto
import ru.nda.users.domain.entity.Coordinates
import ru.nda.users.domain.entity.Dob
import ru.nda.users.domain.entity.Gender
import ru.nda.users.domain.entity.Location
import ru.nda.users.domain.entity.Login
import ru.nda.users.domain.entity.Name
import ru.nda.users.domain.entity.Picture
import ru.nda.users.domain.entity.Registered
import ru.nda.users.domain.entity.Street
import ru.nda.users.domain.entity.Timezone
import ru.nda.users.domain.entity.User

fun UserDto.toUser() = User(
    gender = when (gender) {
        "male" -> Gender.MALE
        "female" -> Gender.FEMALE
        else -> Gender.UNDEFINED
    },
    name = name.toName(),
    location = location.toLocation(),
    email = email,
    login = login.toLogin(),
    dob = dob.toDob(),
    registered = registered.toRegistered(),
    picture = picture.toPicture(),
    phone = phone
)

fun List<UserDto>.toUsers() = map { userDto -> userDto.toUser() }

private fun LoginDto.toLogin() = Login(
    username = username,
    password = password
)

private fun DobDto.toDob() = Dob(
    date = date,
    age = age
)

private fun NameDto.toName() = Name(
    firstName = firstName,
    lastName = lastName
)

private fun PictureDto.toPicture() = Picture(
    large = large,
    medium = medium,
    thumbnail = thumbnail
)

private fun RegisteredDto.toRegistered() = Registered(
    date = date,
    age = age
)

private fun LocationDto.toLocation() = Location(
    street = streetDto.toStreet(),
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    coordinates = coordinatesDto.toCoordinates(),
    timezone = timezoneDto.toTimezone()
)

private fun StreetDto.toStreet() = Street(
    number = number,
    name = name
)

private fun CoordinatesDto.toCoordinates() = Coordinates(
    latitude = latitude,
    longitude = longitude
)

private fun TimezoneDto.toTimezone() = Timezone(
    offset = offset,
    description = description
)