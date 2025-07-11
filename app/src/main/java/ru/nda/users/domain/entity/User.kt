package ru.nda.users.domain.entity

import ru.nda.paging.domain.Item

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
) : Item
