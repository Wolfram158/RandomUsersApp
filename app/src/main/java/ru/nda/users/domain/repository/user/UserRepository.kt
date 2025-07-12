package ru.nda.users.domain.repository.user

interface UserRepository {
    fun showMap(latitude: String, longitude: String)
}