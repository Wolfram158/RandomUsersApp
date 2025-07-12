package ru.nda.users.domain.entity.user

import android.os.Parcelable
import ru.nda.paging.domain.Item
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val gender: Gender,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val picture: Picture,
    val phone: String
) : Item, Parcelable
