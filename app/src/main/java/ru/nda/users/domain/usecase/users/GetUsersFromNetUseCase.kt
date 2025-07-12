package ru.nda.users.domain.usecase.users

import ru.nda.users.di.AppScope
import ru.nda.users.domain.repository.users.UsersRepository
import javax.inject.Inject

@AppScope
class GetUsersFromNetUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(page: Int, counts: Int = 10, seed: String = "seed") =
        repository.getUsersFromNet(
            page = page,
            counts = counts,
            seed = seed
        )
}