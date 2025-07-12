package ru.nda.users.data.database.users.dbo

import androidx.room.Entity

@Entity(
    tableName = "users",
    primaryKeys = [
        "genderDbo",
        "nameDbo",
        "locationDbo",
        "email",
        "loginDbo",
        "dobDbo",
        "registeredDbo",
        "pictureDbo",
        "phone"
    ]
)
data class UserDbo(
    val genderDbo: GenderDbo,
    val nameDbo: NameDbo,
    val locationDbo: LocationDbo,
    val email: String,
    val loginDbo: LoginDbo,
    val dobDbo: DobDbo,
    val registeredDbo: RegisteredDbo,
    val pictureDbo: PictureDbo,
    val phone: String
)
