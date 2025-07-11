package ru.nda.users.domain.entity

enum class Gender {
    MALE, FEMALE, UNDEFINED;

    override fun toString(): String {
        return when (this) {
            MALE -> "male"
            FEMALE -> "female"
            UNDEFINED -> "undefined"
        }
    }
}