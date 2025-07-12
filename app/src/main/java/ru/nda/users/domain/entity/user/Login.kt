package ru.nda.users.domain.entity.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val username: String,
    val password: String
) : Parcelable
