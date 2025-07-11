package ru.nda.users.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dob(
    val date: String,
    val age: String
) : Parcelable
