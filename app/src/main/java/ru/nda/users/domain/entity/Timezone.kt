package ru.nda.users.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timezone(
    val offset: String,
    val description: String
) : Parcelable
