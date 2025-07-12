package ru.nda.users.domain.repository.users

import ru.nda.users.domain.entity.user.User

interface UsersRepository {
    suspend fun getUsersFromNet(page: Int, counts: Int, seed: String): List<User>

    suspend fun insertUsers(users: List<User>)

    suspend fun getUsersFromDb(): List<User>

    suspend fun deleteAll()
}