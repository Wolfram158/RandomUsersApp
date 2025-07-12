package ru.nda.users.domain.usecase.users

import ru.nda.users.di.AppScope
import ru.nda.users.domain.entity.user.User
import ru.nda.users.domain.repository.users.UsersRepository
import javax.inject.Inject

@AppScope
class InsertUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(users: List<User>) = repository.insertUsers(users)
}