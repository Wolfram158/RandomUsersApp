package ru.nda.users.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val username: String,
    val password: String
) : Parcelable
