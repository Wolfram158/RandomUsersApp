package ru.nda.users.domain.usecase.user

import ru.nda.users.di.AppScope
import ru.nda.users.domain.repository.user.UserRepository
import javax.inject.Inject

@AppScope
class MailtoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(email: String) = repository.mailto(email)
}