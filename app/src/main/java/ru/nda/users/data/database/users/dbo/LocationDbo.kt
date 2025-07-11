package ru.nda.users.data.database.users.dbo

data class LocationDbo(
    val streetDbo: StreetDbo,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinatesDbo: CoordinatesDbo,
    val timezoneDbo: TimezoneDbo
)
