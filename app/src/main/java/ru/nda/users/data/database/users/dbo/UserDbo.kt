package ru.nda.users.data.database.users.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbo(
    @PrimaryKey(autoGenerate = true) val id: Int,
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
