package ru.nda.users.data.repository.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nda.users.data.mapper.toUsers
import ru.nda.users.data.network.ApiService
import ru.nda.users.domain.entity.User
import ru.nda.users.domain.repository.users.UsersRepository
import ru.nda.users.domain.state.State
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UsersRepository {
    override fun getUsers(page: Int, counts: Int, seed: String): Flow<State<List<User>>> {
        return flow {
            try {
                emit(
                    State.Success(
                        apiService.getUsers(
                            page = page,
                            results = counts,
                            seed = seed
                        ).results.toUsers()
                    )
                )
            } catch (_: Exception) {
                emit(State.Error())
            }
        }
    }
}