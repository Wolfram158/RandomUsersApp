package ru.nda.users.data.mapper

import ru.nda.users.data.database.users.dbo.CoordinatesDbo
import ru.nda.users.data.database.users.dbo.DobDbo
import ru.nda.users.data.database.users.dbo.GenderDbo
import ru.nda.users.data.database.users.dbo.LocationDbo
import ru.nda.users.data.database.users.dbo.LoginDbo
import ru.nda.users.data.database.users.dbo.NameDbo
import ru.nda.users.data.database.users.dbo.PictureDbo
import ru.nda.users.data.database.users.dbo.RegisteredDbo
import ru.nda.users.data.database.users.dbo.StreetDbo
import ru.nda.users.data.database.users.dbo.TimezoneDbo
import ru.nda.users.data.database.users.dbo.UserDbo
import ru.nda.users.domain.entity.user.Coordinates
import ru.nda.users.domain.entity.user.Dob
import ru.nda.users.domain.entity.user.Gender
import ru.nda.users.domain.entity.user.Location
import ru.nda.users.domain.entity.user.Login
import ru.nda.users.domain.entity.user.Name
import ru.nda.users.domain.entity.user.Picture
import ru.nda.users.domain.entity.user.Registered
import ru.nda.users.domain.entity.user.Street
import ru.nda.users.domain.entity.user.Timezone
import ru.nda.users.domain.entity.user.User

fun UserDbo.toUser() = User(
    gender = when (genderDbo) {
        GenderDbo.MALE -> Gender.MALE
        GenderDbo.FEMALE -> Gender.FEMALE
        GenderDbo.UNDEFINED -> Gender.UNDEFINED
    },
    name = nameDbo.toName(),
    location = locationDbo.toLocation(),
    email = email,
    login = loginDbo.toLogin(),
    dob = dobDbo.toDob(),
    registered = registeredDbo.toRegistered(),
    picture = pictureDbo.toPicture(),
    phone = phone
)

fun List<UserDbo>.toUsers() = map { userDto -> userDto.toUser() }

private fun LoginDbo.toLogin() = Login(
    username = username,
    password = password
)

private fun DobDbo.toDob() = Dob(
    date = date,
    age = age
)

private fun NameDbo.toName() = Name(
    firstName = firstName,
    lastName = lastName
)

private fun PictureDbo.toPicture() = Picture(
    large = large,
    medium = medium,
    thumbnail = thumbnail
)

private fun RegisteredDbo.toRegistered() = Registered(
    date = date,
    age = age
)

private fun LocationDbo.toLocation() = Location(
    street = streetDbo.toStreet(),
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    coordinates = coordinatesDbo.toCoordinates(),
    timezone = timezoneDbo.toTimezone()
)

private fun StreetDbo.toStreet() = Street(
    number = number,
    name = name
)

private fun CoordinatesDbo.toCoordinates() = Coordinates(
    latitude = latitude,
    longitude = longitude
)

private fun TimezoneDbo.toTimezone() = Timezone(
    offset = offset,
    description = description
)