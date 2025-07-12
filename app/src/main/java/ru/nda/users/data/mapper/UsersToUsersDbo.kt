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

fun User.toUserDbo() = UserDbo(
    genderDbo = when (gender) {
        Gender.MALE -> GenderDbo.MALE
        Gender.FEMALE -> GenderDbo.FEMALE
        Gender.UNDEFINED -> GenderDbo.UNDEFINED
    },
    nameDbo = name.toNameDbo(),
    locationDbo = location.toLocationDbo(),
    email = email,
    loginDbo = login.toLoginDbo(),
    dobDbo = dob.toDobDbo(),
    registeredDbo = registered.toRegisteredDbo(),
    pictureDbo = picture.toPictureDbo(),
    phone = phone
)

fun List<User>.toUsersDbo() = map { userDto -> userDto.toUserDbo() }

private fun Login.toLoginDbo() = LoginDbo(
    username = username,
    password = password
)

private fun Dob.toDobDbo() = DobDbo(
    date = date,
    age = age
)

private fun Name.toNameDbo() = NameDbo(
    firstName = firstName,
    lastName = lastName
)

private fun Picture.toPictureDbo() = PictureDbo(
    large = large,
    medium = medium,
    thumbnail = thumbnail
)

private fun Registered.toRegisteredDbo() = RegisteredDbo(
    date = date,
    age = age
)

private fun Location.toLocationDbo() = LocationDbo(
    streetDbo = street.toStreetDbo(),
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    coordinatesDbo = coordinates.toCoordinatesDbo(),
    timezoneDbo = timezone.toTimezoneDbo()
)

private fun Street.toStreetDbo() = StreetDbo(
    number = number,
    name = name
)

private fun Coordinates.toCoordinatesDbo() = CoordinatesDbo(
    latitude = latitude,
    longitude = longitude
)

private fun Timezone.toTimezoneDbo() = TimezoneDbo(
    offset = offset,
    description = description
)