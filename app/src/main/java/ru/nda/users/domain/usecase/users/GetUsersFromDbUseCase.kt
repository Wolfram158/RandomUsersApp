package ru.nda.users.domain.usecase.users

import ru.nda.users.di.AppScope
import ru.nda.users.domain.repository.users.UsersRepository
import javax.inject.Inject

@AppScope
class GetUsersFromDbUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke() = repository.getUsersFromDb()
}