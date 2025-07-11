package ru.nda.users.domain.repository.users

import kotlinx.coroutines.flow.Flow
import ru.nda.users.domain.entity.User
import ru.nda.users.domain.state.State

interface UsersRepository {
    fun getUsers(page: Int, counts: Int, seed: String): Flow<State<List<User>>>
}