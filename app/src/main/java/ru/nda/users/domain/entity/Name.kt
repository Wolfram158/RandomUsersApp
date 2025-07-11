package ru.nda.users.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    val firstName: String,
    val lastName: String,
) : Parcelable
