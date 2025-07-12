package ru.nda.users.domain.repository.user

interface UserRepository {
    fun showMap(latitude: String, longitude: String)

    fun mailto(email: String)

    fun makePhoneNumber(phone: String)
}