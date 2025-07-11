package ru.nda.users.domain.usecase.users

import ru.nda.users.domain.repository.users.UsersRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    operator fun invoke(page: Int, counts: Int = 10, seed: String = "seed") =
        repository.getUsers(
            page = page,
            counts = counts,
            seed = seed
        )
}