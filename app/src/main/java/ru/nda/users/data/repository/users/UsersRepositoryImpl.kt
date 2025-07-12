package ru.nda.users.data.repository.users

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nda.users.data.database.users.UsersDao
import ru.nda.users.data.mapper.toUsers
import ru.nda.users.data.mapper.toUsersDbo
import ru.nda.users.data.network.ApiService
import ru.nda.users.domain.entity.user.User
import ru.nda.users.domain.repository.users.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao
) : UsersRepository {
    override suspend fun getUsersFromNet(page: Int, counts: Int, seed: String): List<User> {
        return withContext(Dispatchers.IO) {
            apiService.getUsers(
                page = page,
                results = counts,
                seed = seed
            ).results.toUsers()
        }
    }

    override suspend fun insertUsers(users: List<User>) {
        withContext(Dispatchers.IO) {
            usersDao.insertUsers(users.toUsersDbo())
        }
    }

    override suspend fun getUsersFromDb(): List<User> {
        return withContext(Dispatchers.IO) {
            usersDao.getUsers().toUsers()
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            usersDao.deleteAll()
        }
    }
}
