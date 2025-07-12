package ru.nda.users.domain.entity.user

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